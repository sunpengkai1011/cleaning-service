package hottopic.mit.co.nz.cleaningservice.network;

public interface RequestCallBack<T> {
    void requestCallBack(T t);
    void requestFailure(T t);
}
