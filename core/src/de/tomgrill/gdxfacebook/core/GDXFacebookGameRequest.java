package de.tomgrill.gdxfacebook.core;

import com.badlogic.gdx.utils.Array;

public class GDXFacebookGameRequest {
	private String message;
	private Array<String> recipients;
	private String data;
	private String title;
	private ActionType actionType;
	private String objectId;
	private Filters filters;
	private Array<String> suggestions;

	/**
	 * Sets the message users receiving the request will see. The maximum length is 60 characters.
	 *
	 * @param message message
	 */
	public GDXFacebookGameRequest setMessage(String message) {
		this.message = message;
		return this;
	}

	/**
	 * An array of user IDs, usernames or invite tokens (String) of people to send request.
	 *
	 * These may or may not be a friend of the sender. If this is specified by the app,
	 * the sender will not have a choice of recipients. If not, the sender will see a multi-friend selector
	 *
	 * @param recipients
	 */
	public void setRecipients(Array<String> recipients) {
		this.recipients = recipients;
	}

	/**
	 * Sets optional data which can be used for tracking; maximum length is 255 characters.
	 *
	 * @param data data
	 */
	public GDXFacebookGameRequest setData(String data) {
		this.data = data;
		return this;
	}

	/**
	 * Sets an optional title for the dialog; maximum length is 50 characters.
	 *
	 * @param title title
	 */
	public GDXFacebookGameRequest setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Sets the action type for this request
	 *
	 * @param actionType actionType
	 */
	public GDXFacebookGameRequest setActionType(ActionType actionType) {
		this.actionType = actionType;
		return this;
	}

	/**
	 * Sets the open graph id of the object that action type will be performed on Only valid (and required) for ActionTypes SEND, ASKFOR
	 *
	 * @param objectId objectId
	 */
	public GDXFacebookGameRequest setObjectId(String objectId) {
		this.objectId = objectId;
		return this;
	}

	/**
	 * Sets the filters for everybody/app users/non app users
	 *
	 * @param filters filters
	 */
	public GDXFacebookGameRequest setFilters(Filters filters) {
		this.filters = filters;
		return this;
	}

	/**
	 * Sets a list of user ids suggested as request receivers
	 *
	 * @param suggestions suggestions
	 */
	public GDXFacebookGameRequest setSuggestions(Array<String> suggestions) {
		this.suggestions = suggestions;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public String getData() {
		return data;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public String getObjectId() {
		return objectId;
	}

	public Filters getFilters() {
		return filters;
	}

	public Array<String> getSuggestions() {
		return suggestions;
	}

	public String getTitle() {
		return title;
	}

	public Array<String> getRecipients() {
		return recipients;
	}


	public enum ActionType {
		SEND, ASKFOR, TURN
	}

	public enum Filters {
		APP_USERS, APP_NON_USERS
	}
}
