package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OutstandingOrderModel {
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("order_data")
    @Expose
    private List<OrderData> orderData = null;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<OrderData> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderData> orderData) {
        this.orderData = orderData;
    }


    public class OrderData {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("consumer_id")
        @Expose
        private String consumerId;
        @SerializedName("consumer_fullname")
        @Expose
        private String consumerFullname;
        @SerializedName("business_user_id")
        @Expose
        private String businessUserId;
        @SerializedName("businessuser_fullname")
        @Expose
        private String businessuserFullname;
        @SerializedName("gross_amount")
        @Expose
        private String grossAmount;
        @SerializedName("discount_amount")
        @Expose
        private Object discountAmount;
        @SerializedName("tax_amount")
        @Expose
        private Object taxAmount;
        @SerializedName("net_amount")
        @Expose
        private String netAmount;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("paypal_transaction_id")
        @Expose
        private Object paypalTransactionId;
        @SerializedName("updated_by")
        @Expose
        private String updatedBy;
        @SerializedName("updated_on")
        @Expose
        private String updatedOn;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getConsumerId() {
            return consumerId;
        }

        public void setConsumerId(String consumerId) {
            this.consumerId = consumerId;
        }

        public String getConsumerFullname() {
            return consumerFullname;
        }

        public void setConsumerFullname(String consumerFullname) {
            this.consumerFullname = consumerFullname;
        }

        public String getBusinessUserId() {
            return businessUserId;
        }

        public void setBusinessUserId(String businessUserId) {
            this.businessUserId = businessUserId;
        }

        public String getBusinessuserFullname() {
            return businessuserFullname;
        }

        public void setBusinessuserFullname(String businessuserFullname) {
            this.businessuserFullname = businessuserFullname;
        }

        public String getGrossAmount() {
            return grossAmount;
        }

        public void setGrossAmount(String grossAmount) {
            this.grossAmount = grossAmount;
        }

        public Object getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(Object discountAmount) {
            this.discountAmount = discountAmount;
        }

        public Object getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(Object taxAmount) {
            this.taxAmount = taxAmount;
        }

        public String getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(String netAmount) {
            this.netAmount = netAmount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getPaypalTransactionId() {
            return paypalTransactionId;
        }

        public void setPaypalTransactionId(Object paypalTransactionId) {
            this.paypalTransactionId = paypalTransactionId;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            this.updatedOn = updatedOn;
        }
    }
}
