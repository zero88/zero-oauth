package com.zero.oauth.core.event;

public enum ThreadMode {
    /**
     * Subscribers will be called in root thread of application.
     */
    MAIN,
    /**
     * Subscribers will be called in the same thread posting the event.
     */
    POSTING,
    /**
     * Subscribers will be called in a {@code background} thread. If {@code posting} thread is {@code main} thread,
     * {@code EventBus} will init a single background thread that spreads events sequentially.
     */
    BACKGROUND,
    /**
     * Subscribers will be called in a {@code separate} thread. This is always independent from the {@code posting}
     * thread and the {@code main} thread. Posting events never wait for event handler methods using this mode.
     */
    ASYNC
}
