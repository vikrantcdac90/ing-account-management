package nl.ing.accounts.domain.response;


public class AccountApiResponse<T> {
    private boolean success;
    private String message;
    private T response;

    public AccountApiResponse() {
    }

    public AccountApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AccountApiResponse(boolean success, String message, T response) {
        this.success = success;
        this.message = message;
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return response;
    }

    public void setData(T response) {
        this.response = response;
    }
}
