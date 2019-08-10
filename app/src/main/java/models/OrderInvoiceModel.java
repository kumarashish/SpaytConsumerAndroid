package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderInvoiceModel {
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
    private List<OrderDatum> orderData = null;

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

    public List<OrderDatum> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderDatum> orderData) {
        this.orderData = orderData;
    }

    public class OrderDatum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("consumer_id")
        @Expose
        private String consumerId;
        @SerializedName("consumer_email")
        @Expose
        private Object consumerEmail;
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
        @SerializedName("created_on")
        @Expose
        private String createdOn;
        @SerializedName("created_month")
        @Expose
        private String createdMonth;
        @SerializedName("created_year")
        @Expose
        private String createdYear;
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

        public Object getConsumerEmail() {
            return consumerEmail;
        }

        public void setConsumerEmail(Object consumerEmail) {
            this.consumerEmail = consumerEmail;
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

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        public String getCreatedMonth() {
            return createdMonth;
        }

        public void setCreatedMonth(String createdMonth) {
            this.createdMonth = createdMonth;
        }

        public String getCreatedYear() {
            return createdYear;
        }

        public void setCreatedYear(String createdYear) {
            this.createdYear = createdYear;
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
