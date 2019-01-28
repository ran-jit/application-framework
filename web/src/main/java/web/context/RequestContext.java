package web.context;

import web.request.RequestInfo;

public class RequestContext {
    private static final ThreadLocal<RequestInfo> REQUEST_CONTEXT = new ThreadLocal<>();

    public static void set(RequestInfo requestInfo) {
        REQUEST_CONTEXT.set(requestInfo);
    }

    public static RequestInfo get() {
        RequestInfo requestInfo = REQUEST_CONTEXT.get();
        return requestInfo == null ? RequestInfo.builder().build() : requestInfo;
    }

    public static void remove() {
        if (REQUEST_CONTEXT.get() != null) {
            REQUEST_CONTEXT.remove();
        }
    }
}
