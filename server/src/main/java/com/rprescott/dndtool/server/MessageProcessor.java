package com.rprescott.dndtool.server;

public interface MessageProcessor<REQ, RESP> {

    RESP processMessage(REQ message);

}
