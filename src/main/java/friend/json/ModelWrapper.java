package friend.json;

import java.util.List;

public class ModelWrapper {

    private String dataType;
    private List<?> dataList;

    public ModelWrapper() {

    }

    public ModelWrapper(List<?> data) {
        this.dataList = data;
        setDataType(data);
    }

    public String getDataType() {
        return dataType;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
        setDataType(dataList);
    }

    private void setDataType(List<?> data) {
        if (!data.isEmpty()) {
            this.dataType = data.get(0).getClass().getSimpleName().toLowerCase();
        } else {
            this.dataType = "none";
        }
    }

}
