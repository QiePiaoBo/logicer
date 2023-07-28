package com.dylan.logicer.base.logicer;

public enum LogicerSubProtocol {
    LOGICER(0),
    TALK(1),
    COMMAND(2),
    HEARTBEAT(3)
    ;

    private final int code;

    LogicerSubProtocol(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
    /**
     * 根据code获取对应的枚举
     * @param code
     * @return
     */
    public static LogicerSubProtocol getProtocolByCode(int code){
        switch (code){
            case 0:
                return LogicerSubProtocol.LOGICER;
            case 1:
                return LogicerSubProtocol.TALK;
            case 2:
                return LogicerSubProtocol.COMMAND;
            case 3:
                return LogicerSubProtocol.HEARTBEAT;
            default:
                return LogicerSubProtocol.LOGICER;
        }
    }

}
