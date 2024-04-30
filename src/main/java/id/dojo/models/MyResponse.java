package id.dojo.models;

public class MyResponse<T> {
    public MetaData metaData;
    public  T data;
    public MyResponse(MetaData metaData, T data){
        this.metaData = metaData;
        this.data = data;
    }

    public MyResponse(int status, String message, T data){
        this.metaData = new MetaData(status, message);
        this.data = data;
    }

}
