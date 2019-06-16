package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class OutstandingOrderModel {

    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("order_data")
    @Expose
    private OrderData orderData;
    @SerializedName("order_details_data")
    @Expose
    private OrderDetailsData orderDetailsData;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OrderData getOrderData() {
        return orderData;
    }

    public void setOrderData(OrderData orderData) {
        this.orderData = orderData;
    }

    public OrderDetailsData getOrderDetailsData() {
        return orderDetailsData;
    }

    public void setOrderDetailsData(OrderDetailsData orderDetailsData) {
        this.orderDetailsData = orderDetailsData;
    }


    public class OrderData {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("consumer_id")
        @Expose
        private String consumerId;
        @SerializedName("business_user_id")
        @Expose
        private String businessUserId;
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

        public String getBusinessUserId() {
            return businessUserId;
        }

        public void setBusinessUserId(String businessUserId) {
            this.businessUserId = businessUserId;
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


        public class OrderDetailsData {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("consumer_order_id")
            @Expose
            private String consumerOrderId;
            @SerializedName("business_location_id")
            @Expose
            private String businessLocationId;
            @SerializedName("business_product_id")
            @Expose
            private String businessProductId;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("product_image_url")
            @Expose
            private Object productImageUrl;
            @SerializedName("business_product_offer_id")
            @Expose
            private Object businessProductOfferId;
            @SerializedName("offer_name")
            @Expose
            private Object offerName;
            @SerializedName("offer_image_url")
            @Expose
            private Object offerImageUrl;
            @SerializedName("quantity")
            @Expose
            private String quantity;
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

            public String getConsumerOrderId() {
                return consumerOrderId;
            }

            public void setConsumerOrderId(String consumerOrderId) {
                this.consumerOrderId = consumerOrderId;
            }

            public String getBusinessLocationId() {
                return businessLocationId;
            }

            public void setBusinessLocationId(String businessLocationId) {
                this.businessLocationId = businessLocationId;
            }

            public String getBusinessProductId() {
                return businessProductId;
            }

            public void setBusinessProductId(String businessProductId) {
                this.businessProductId = businessProductId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getProductImageUrl() {
                return productImageUrl;
            }

            public void setProductImageUrl(Object productImageUrl) {
                this.productImageUrl = productImageUrl;
            }

            public Object getBusinessProductOfferId() {
                return businessProductOfferId;
            }

            public void setBusinessProductOfferId(Object businessProductOfferId) {
                this.businessProductOfferId = businessProductOfferId;
            }

            public Object getOfferName() {
                return offerName;
            }

            public void setOfferName(Object offerName) {
                this.offerName = offerName;
            }

            public Object getOfferImageUrl() {
                return offerImageUrl;
            }

            public void setOfferImageUrl(Object offerImageUrl) {
                this.offerImageUrl = offerImageUrl;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
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


