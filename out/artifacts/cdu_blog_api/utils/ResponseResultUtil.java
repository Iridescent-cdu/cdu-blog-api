package utils;

/**
 * 定义一个泛型类：便于包裹处理结果
 *
 * @param <T>
 */
public class ResponseResultUtil<T> {
    private Boolean success;
    private Integer code;
    private T data;
    private String message;


    public ResponseResultUtil(Boolean success, Integer code, T data, String message) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.message = message;
    }
}
