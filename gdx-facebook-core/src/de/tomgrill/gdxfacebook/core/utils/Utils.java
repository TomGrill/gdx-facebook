/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package de.tomgrill.gdxfacebook.core.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class Utils {

    public static String permissionsArrayToString(Array<String> permissions) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < permissions.size; i++) {

            stringBuilder.append(permissions.get(i));
            if (i + 1 < permissions.size) {
                stringBuilder.append(",");
            }
        }

        return stringBuilder.toString();
    }

    public static boolean isValidSuccessfulLoginURL(String url) {
        try {
            URL urlObj = new URL(url);
            if (!urlObj.getProtocol().equals("https")) {
                return false;
            }
            if (!urlObj.getAuthority().equals("www.facebook.com")) {
                return false;
            }
            if (!urlObj.getHost().equals("www.facebook.com")) {
                return false;
            }

            if (!urlObj.getPath().equals("/connect/login_success.html")) {
                return false;
            }

            String ref = urlObj.getRef();

            if (ref == null) {
                return false;
            }

            if (!ref.contains("access_token=")) {
                return false;
            }

            return ref.contains("expires_in=");
        } catch (MalformedURLException e) {
        }
        return false;
    }

    public static ArrayMap<String, String> parseQuery(String query) throws UnsupportedEncodingException {
        ArrayMap<String, String> params = new ArrayMap<String, String>();
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            String key = URLDecoder.decode(pair[0], "UTF-8");
            String value = URLDecoder.decode(pair[1], "UTF-8");
            params.put(key, value);
        }


        return params;
    }

    public static boolean isValidErrorLoginURL(String url) {
        try {
            URL urlObj = new URL(url);
            if (!urlObj.getProtocol().equals("https")) {
                return false;
            }
            if (!urlObj.getAuthority().equals("www.facebook.com")) {
                return false;
            }
            if (!urlObj.getHost().equals("www.facebook.com")) {
                return false;
            }

            if (!urlObj.getPath().equals("/connect/login_success.html")) {
                return false;
            }

            String query = urlObj.getQuery();

            if (query == null) {
                return false;
            }

            if (!query.contains("error=")) {
                return false;
            }

            if (!query.contains("error_code=")) {
                return false;
            }

            if (!query.contains("error_description=")) {
                return false;
            }

            return query.contains("error_reason=");

        } catch (MalformedURLException e) {
        }
        return false;
    }
}
