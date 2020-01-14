CREATE TABLE HISTORICAL_QUERY_SHIPPING
(
    historical_id       BIGINT PRIMARY KEY auto_increment,
    shipping_code       VARCHAR2(255),
    shipping_status     VARCHAR2(255),
    shipping_sub_status VARCHAR2(255),
    date_time           DATETIME
)