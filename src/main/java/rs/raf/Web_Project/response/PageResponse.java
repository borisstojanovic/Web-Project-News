package rs.raf.Web_Project.response;

public class PageResponse {
    Object data;
    int count;

    public PageResponse(){

    }

    public PageResponse(Object data, int count) {
        this.data = data;
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
