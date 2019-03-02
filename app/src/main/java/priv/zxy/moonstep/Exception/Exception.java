package priv.zxy.moonstep.Exception;

/**
 * 创建人: zhang376358913
 * 创建时间: 2019/3/1 17:25
 * 类描述: 自定义抽象异常类
 * 修改人: zhang376358913
 * 修改时间: zhang376358913
 * 修改备注:
 */
public abstract class Exception extends java.lang.Exception {

    private ExceptionCode code = ExceptionCode.ERROR_EXCEPTION;

    public Exception(String message) {
        super(message);
    }

    public Exception(String message, ExceptionCode code) {
        super(message);
        setCode(code);
    }

    public Exception(String message, ExceptionCode code, Throwable cause) {
        super(message, cause);
        setCode(code);
    }

    public void setCode(ExceptionCode code) {
        this.code = code;
    }

    public ExceptionCode getCode() {
        return code;
    }

    public void handleException() {
     ExceptionManager.handleException(this.code);
    }

    abstract void handleWay();
}
