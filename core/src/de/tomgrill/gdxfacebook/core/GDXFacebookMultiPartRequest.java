/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.tomgrill.gdxfacebook.core;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Build a proper mulitpart Facebook Graph API request with this class.
 *
 * @author Thomas Pronold (TomGrill) mail@tomgrill.de
 * @see <a href="https://developers.facebook.com/docs/graph-api/using-graph-api/">https://developers.facebook.com/docs/graph-api/using-graph-api/</a>
 */
public class GDXFacebookMultiPartRequest extends AbstractRequest {

    private static int boundaryCounter = 0;

    private ArrayMap<String, String> headers = new ArrayMap<String, String>();

    private Array<FileHandle> fileHandles = new Array<FileHandle>();
    private Array<String> contentTypes = new Array<String>();

    private String boundary;

    public GDXFacebookMultiPartRequest() {
        boundary = generateBoundary();
        headers.put("Content-Type", "multipart/form-data; boundary=" + boundary);
    }

    private static byte[] loadFile(FileHandle fileHandle) throws IOException {
        InputStream is = fileHandle.read();

        long length = fileHandle.length();
        if (length > Integer.MAX_VALUE) {
            throw new IOException("File size to large");
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + fileHandle.name());
        }

        is.close();
        return bytes;
    }

    private synchronized String generateBoundary() {
        boundaryCounter++;
        return Long.toHexString(TimeUtils.nanoTime()) + boundaryCounter;
    }

    @Override
    public ArrayMap<String, String> getHeaders() {
        return headers;
    }

    @Override
    public final String getContent() {
        return null;
    }

    @Override
    public InputStream getContentStream() throws IOException {
        ByteArrayOutputStream op = new ByteArrayOutputStream();


        if (fileHandles.size > 1) {

            // TODO this part is not yet used. See issue #13 @github
            String groupBoundary = generateBoundary();
            String openGroup = "";
            openGroup += "--" + boundary + "\r\n";
            openGroup += "Content-Disposition: form-data; name=\"files\"" + "\r\n";
            openGroup += "Content-Type: multipart/mixed; boundary=" + groupBoundary + "\r\n";

            op.write(openGroup.getBytes());

            for (int i = 0; i < fileHandles.size; i++) {
                FileHandle fileHandle = fileHandles.get(i);


                String fileString = "";
                fileString += "\r\n" + "--" + groupBoundary + "\r\n";
                fileString += "Content-Disposition: file; filename=\"" + fileHandle.name() + "\"" + "\r\n";
                fileString += "Content-Type: image/png" + "\r\n";
                fileString += "Content-Transfer-Encoding: binary" + "\r\n";
                fileString += "\r\n";

                op.write(fileString.getBytes());
                op.write(loadFile(fileHandle));

                if (i + 1 == fileHandles.size) {
                    fileString = "\r\n" + "--" + groupBoundary + "--";
                    op.write(fileString.getBytes());
                }

            }


        } else {
            for (int i = 0; i < fileHandles.size; i++) {
                FileHandle fileHandle = fileHandles.get(i);
                String contentType = contentTypes.get(i);
                String fileString = "";
                fileString += "--" + boundary + "\r\n";
                fileString += "Content-Disposition: form-data; name=\"files" + i + "\"; filename=\"" + fileHandle.name() + "\"" + "\r\n";
                fileString += "Content-Type: " + contentType + "\r\n";
                fileString += "Content-Transfer-Encoding: binary" + "\r\n";
                fileString += "\r\n";

                op.write(fileString.getBytes());
                op.write(fileHandle.readBytes());


            }
        }


        for (int i = 0; i < fields.size; i++) {

            String key = fields.getKeyAt(i);
            String value = fields.getValueAt(i);

            String fieldString = "";
            fieldString += "\r\n" + "--" + boundary + "\r\n";
            fieldString += "Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n";
            fieldString += "Content-Type: text/plain; charset=UTF-8" + "\r\n";
            fieldString += "\r\n";
            fieldString += value;

            if (i + 1 == fields.size) {
                fieldString += "\r\n" + "--" + boundary + "--" + "\r\n";
            }

            op.write(fieldString.getBytes());
        }

        return new ByteArrayInputStream(op.toByteArray());
    }

    public final GDXFacebookMultiPartRequest setFileHandle(FileHandle fileHandle, String contentType) {
        // TODO prevent adding multiple files,
        // because I am not sure if it is possible at all to upload multiple photos at all.
        // Need to do research on this.

        fileHandles.clear();
        contentTypes.clear();

        fileHandles.add(fileHandle);
        contentTypes.add(contentType);


        return this;
    }

    @Override
    public final String getMethod() {
        return Net.HttpMethods.POST;
    }

    @Override
    public final GDXFacebookMultiPartRequest setMethod(String method) {
        return this;
    }

    @Override
    public GDXFacebookMultiPartRequest useCurrentAccessToken() {
        return (GDXFacebookMultiPartRequest) super.useCurrentAccessToken();
    }

    @Override
    public GDXFacebookMultiPartRequest setNode(String node) {
        return (GDXFacebookMultiPartRequest) super.setNode(node);
    }


    @Override
    public GDXFacebookMultiPartRequest setTimeout(int timeout) {
        return (GDXFacebookMultiPartRequest) super.setTimeout(timeout);
    }

    @Override
    public GDXFacebookMultiPartRequest putField(String key, String value) {
        return (GDXFacebookMultiPartRequest) super.putField(key, value);
    }

    @Override
    public GDXFacebookMultiPartRequest putFields(ArrayMap<String, String> fields) {
        return (GDXFacebookMultiPartRequest) super.putFields(fields);
    }

    public String getJavascriptObjectString() {
        return "";
    }
}
