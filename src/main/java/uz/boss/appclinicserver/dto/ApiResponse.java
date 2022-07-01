package uz.boss.appclinicserver.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author Murtazayev Muhammad
 * @since 25.12.2021
 */
@Setter
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private HttpStatus status;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.status = HttpStatus.OK;
    }

    public ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
        this.status = HttpStatus.OK;
    }

    public ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.status = HttpStatus.OK;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }


    public static <E> ApiResponse<E> successResponse(E data) {
        return new ApiResponse<>(true, data);
    }

    public static <E> ApiResponse<E> successResponse(E data, String message) {
        return new ApiResponse<>(true, data, message);
    }

    public static <E> ApiResponse<E> successResponse(String message) {
        return new ApiResponse<>(true, message);
    }

    public static <E> ApiResponse<E> error(String message, HttpStatus status) {
        return new ApiResponse<>(false, message, status);
    }

    public static <E> ApiResponse<E> notFound(String message) {
        return new ApiResponse<>(false, message, HttpStatus.NOT_FOUND);
    }
}
