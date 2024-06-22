package io.github.maidsg.starter.voyage.model.base;

import io.github.maidsg.starter.voyage.constant.ApiVersionConstant;
import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/*******************************************************************
 * <pre></pre>
 * @文件名称： ApiObject.java
 * @包 路  径： io.github.maidsg.starter.voyage.model.base
 * @Copyright：wy (C) 2024 *
 * @Description:
 * @Version: V1.0
 * @Author： wy
 * @Date： 2024/6/22 09:23
 * @Modify：
 */
@Data
public class ApiObject implements Comparable<ApiObject>{

    private int high = 1;

    private int mid = 0;

    private int low = 0;

    public static final ApiObject API_ITEM_DEFAULT = ApiConverter.convert(ApiVersionConstant.DEFAULT_VERSION);

    public ApiObject() {
    }

    @Override
    public int compareTo(ApiObject right) {
        if (this.getHigh() > right.getHigh()) {
            return 1;
        } else if (this.getHigh() < right.getHigh()) {
            return -1;
        }

        if (this.getMid() > right.getMid()) {
            return 1;
        } else if (this.getMid() < right.getMid()) {
            return -1;
        }
        if (this.getLow() > right.getLow()) {
            return 1;
        } else if (this.getLow() < right.getLow()) {
            return -1;
        }

        return 0;
    }





    public static class ApiConverter {

        public static ApiObject convert(String api) {
            ApiObject apiItem = new ApiObject();
            if (ObjectUtils.isEmpty(api)) {
                return apiItem;
            }
            // 1.0.0 -> 1,0,0
            String[] cells = api.split("\\.");
            apiItem.setHigh(Integer.parseInt(cells[0]));
            if (cells.length > 1) {
                apiItem.setMid(Integer.parseInt(cells[1]));
            }

            if (cells.length > 2) {
                apiItem.setLow(Integer.parseInt(cells[2]));
            }

            return apiItem;
        }

    }

    public static void main(String[] args) {
        System.out.println(ApiObject.ApiConverter.convert(ApiVersionConstant.DEFAULT_VERSION));
    }



}
