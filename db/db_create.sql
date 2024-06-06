create table address
(
    id              int auto_increment
        primary key,
    member_id       int          not null,
    zip_code        int          not null,
    primary_address varchar(255) not null,
    address_details varchar(100) not null,
    constraint address_member_id_fk
        foreign key (member_id) references member (id)
            on update cascade on delete cascade
);

create table board
(
    id        int auto_increment
        primary key,
    type      int                                not null,
    member_id int                                not null,
    title     varchar(50)                        not null,
    contents  longtext                           not null,
    secret    tinyint  default 0                 null,
    w_date    datetime default CURRENT_TIMESTAMP null,
    v_count   int      default 0                 null,
    constraint board_ibfk_1
        foreign key (type) references board_type (id),
    constraint board_ibfk_2
        foreign key (member_id) references member (id),
    check (`secret` in (0, 1))
);

create index member_id
    on board (member_id);

create index type
    on board (type);

create table board_type
(
    id   int auto_increment
        primary key,
    name varchar(20) not null
);

create table cart
(
    id             int auto_increment
        primary key,
    order_id       int           not null,
    order_item     int           not null,
    order_price    int           not null,
    count          int default 1 not null,
    origin_price   int           not null,
    discount_price int           null,
    coupon_check   int default 0 not null,
    constraint cart_ibfk_1
        foreign key (order_id) references `order` (id),
    constraint cart_ibfk_2
        foreign key (order_item) references item (id),
    check (`coupon_check` in (0, 1))
);

create index Cart_ibfk_1
    on cart (order_id);

create index order_item
    on cart (order_item);

create table coupon
(
    id             int         not null
        primary key,
    code           varchar(50) not null,
    discount       int         not null,
    min_price      int         null,
    discount_limit int         null
);

create table delivery
(
    id        int auto_increment
        primary key,
    address   varchar(255)  not null,
    post      int           not null,
    status_id int default 1 not null,
    constraint delivery_ibfk_1
        foreign key (status_id) references delivery_status (id)
            on update cascade
);

create index Delivery_ibfk_1
    on delivery (status_id);

create table delivery_status
(
    id   int auto_increment
        primary key,
    name varchar(30) default '배송 준비' not null
);

create table item
(
    id       int auto_increment
        primary key,
    type     int          not null,
    category int          not null,
    name     varchar(100) not null,
    price    int          not null,
    pic      varchar(255) null,
    detail   longtext     null,
    constraint item_ibfk_1
        foreign key (type) references pet_type (id),
    constraint item_ibfk_2
        foreign key (category) references market_category (id)
);

create index category
    on item (category);

create index type
    on item (type);

create table link_option
(
    link      tinyint default 0 not null
        primary key,
    kakaotalk tinyint default 0 not null,
    google    tinyint default 0 not null,
    naver     tinyint default 0 not null,
    check (`link` in (0, 1)),
    check (`kakaotalk` in (0, 1)),
    check (`google` in (0, 1)),
    check (`naver` in (0, 1))
);

create table market_category
(
    id   int auto_increment
        primary key,
    name varchar(20) not null
);

create table member
(
    id      int auto_increment
        primary key,
    name    varchar(30)                                                                                                               not null,
    userid  varchar(50)                                                                                                               not null,
    userpw  varchar(256)                                                                                                              not null,
    nick    varchar(50)                                                                                                               not null,
    email   varchar(50)                                                                                                               not null,
    phone   varchar(50)                                                                                                               not null,
    ad      tinyint      default 0                                                                                                    not null,
    profile varchar(255) default 'https://itbank-pethub-s3.s3.ap-northeast-2.amazonaws.com/ed8df585-4d03-48e8-9d6e-e266b8f41506.jpeg' not null,
    role    tinyint      default 0                                                                                                    not null,
    link    tinyint      default 0                                                                                                    not null,
    constraint email
        unique (email),
    constraint nick
        unique (nick),
    constraint phone
        unique (phone),
    constraint userid
        unique (userid),
    constraint Member_Link_Option_link_fk
        foreign key (link) references link_option (link)
            on update cascade,
    check (`ad` in (0, 1)),
    constraint role_check
        check (`role` in (0, 1))
);

create index Member_Ad_Option_ad_fk
    on member (ad);

create table member_coupon
(
    id        int auto_increment
        primary key,
    member_id int                                    not null,
    coupon_id int                                    not null,
    end_date  datetime default '9999-12-31 00:00:00' null,
    reg_date  datetime default CURRENT_TIMESTAMP     null,
    constraint member_coupon_ibfk_1
        foreign key (member_id) references member (id),
    constraint member_coupon_ibfk_2
        foreign key (coupon_id) references coupon (id)
);

create index coupon_id
    on member_coupon (coupon_id);

create index member_id
    on member_coupon (member_id);

create table `order`
(
    id           int auto_increment
        primary key,
    member_id    int                                not null,
    delivery_id  int                                not null,
    order_date   datetime default CURRENT_TIMESTAMP null,
    order_status int      default 1                 not null,
    constraint order_ibfk_1
        foreign key (member_id) references member (id)
            on update cascade,
    constraint order_ibfk_2
        foreign key (delivery_id) references delivery (id)
            on update cascade,
    constraint order_ibfk_3
        foreign key (order_status) references order_status (id)
            on update cascade
);

create index Order_ibfk_1
    on `order` (member_id);

create index Order_ibfk_2
    on `order` (delivery_id);

create index Order_ibfk_3
    on `order` (order_status);

create table order_status
(
    id   int auto_increment
        primary key,
    name varchar(50) default '주문 접수' not null
);

create table pet_type
(
    id   int auto_increment
        primary key,
    name varchar(20) not null
);

create table reply
(
    id        int auto_increment
        primary key,
    member_id int                                not null,
    board_id  int                                not null,
    contents  text                               not null,
    w_date    datetime default CURRENT_TIMESTAMP null,
    constraint reply_ibfk_1
        foreign key (member_id) references member (id),
    constraint reply_ibfk_2
        foreign key (board_id) references board (id)
);

create index board_id
    on reply (board_id);

create index member_id
    on reply (member_id);

create table review
(
    id        int auto_increment
        primary key,
    item_id   int  not null,
    member_id int  not null,
    contents  text null,
    rating    int  null,
    constraint review_ibfk_1
        foreign key (item_id) references item (id),
    constraint review_ibfk_2
        foreign key (member_id) references member (id),
    check ((`rating` >= 0) and (`rating` <= 5))
);

create index item_id
    on review (item_id);

create index member_id
    on review (member_id);

create definer = root@`%` view board_view as
select `b`.`id`        AS `id`,
       `b`.`type`      AS `type`,
       `b`.`member_id` AS `member_id`,
       `m`.`nick`      AS `nick`,
       `b`.`title`     AS `title`,
       `b`.`contents`  AS `contents`,
       `b`.`secret`    AS `secret`,
       `b`.`w_date`    AS `w_date`,
       `b`.`v_count`   AS `v_count`
from (`pethub`.`board` `b` join `pethub`.`member` `m` on ((`b`.`member_id` = `m`.`id`)));

create definer = root@`%` view cartitemmemberview as
select `pethub`.`cart`.`id`             AS `cart_id`,
       `pethub`.`cart`.`order_id`       AS `order_id`,
       `pethub`.`cart`.`order_item`     AS `order_item`,
       `pethub`.`cart`.`order_price`    AS `order_price`,
       `pethub`.`cart`.`count`          AS `count`,
       `pethub`.`cart`.`origin_price`   AS `origin_price`,
       `pethub`.`cart`.`discount_price` AS `discount_price`,
       `pethub`.`cart`.`coupon_check`   AS `coupon_check`,
       `pethub`.`item`.`id`             AS `item_id`,
       `pethub`.`item`.`name`           AS `item_name`,
       `pethub`.`item`.`price`          AS `item_price`,
       `pethub`.`member`.`id`           AS `member_id`
from (((`pethub`.`cart` join `pethub`.`item`
        on ((`pethub`.`cart`.`order_item` = `pethub`.`item`.`id`))) join `pethub`.`order`
       on ((`pethub`.`cart`.`order_id` = `pethub`.`order`.`id`))) join `pethub`.`member`
      on ((`pethub`.`order`.`member_id` = `pethub`.`member`.`id`)));

create definer = root@`%` view member_address_view as
select `m`.`id`              AS `id`,
       `m`.`name`            AS `name`,
       `m`.`userid`          AS `userid`,
       `m`.`userpw`          AS `userpw`,
       `m`.`nick`            AS `nick`,
       `m`.`email`           AS `email`,
       `m`.`phone`           AS `phone`,
       `m`.`ad`              AS `ad`,
       `m`.`profile`         AS `profile`,
       `m`.`role`            AS `role`,
       `m`.`link`            AS `link`,
       `a`.`zip_code`        AS `zip_code`,
       `a`.`primary_address` AS `primary_address`,
       `a`.`address_details` AS `address_details`
from (`pethub`.`member` `m` join `pethub`.`address` `a` on ((`m`.`id` = `a`.`member_id`)));

create definer = root@`%` view member_coupons_view as
select `m`.`id`             AS `member_id`,
       `m`.`name`           AS `member_name`,
       `m`.`userid`         AS `user_id`,
       `m`.`nick`           AS `nickname`,
       `m`.`email`          AS `email`,
       `c`.`id`             AS `coupon_id`,
       `c`.`code`           AS `coupon_code`,
       `c`.`discount`       AS `discount`,
       `c`.`min_price`      AS `minimum_price`,
       `c`.`discount_limit` AS `discount_limit`,
       `mc`.`end_date`      AS `coupon_end_date`,
       `mc`.`reg_date`      AS `coupon_reg_date`
from ((`pethub`.`member` `m` join `pethub`.`member_coupon` `mc`
       on ((`m`.`id` = `mc`.`member_id`))) join `pethub`.`coupon` `c` on ((`mc`.`coupon_id` = `c`.`id`)));

create definer = root@`%` view modc as
select `o`.`id`             AS `order_id`,
       `o`.`member_id`      AS `member_id`,
       `m`.`name`           AS `member_name`,
       `m`.`userid`         AS `member_userid`,
       `m`.`nick`           AS `member_nick`,
       `m`.`email`          AS `member_email`,
       `m`.`phone`          AS `member_phone`,
       `m`.`ad`             AS `member_ad`,
       `m`.`profile`        AS `member_profile`,
       `m`.`role`           AS `member_role`,
       `m`.`link`           AS `member_link`,
       `o`.`delivery_id`    AS `delivery_id`,
       `d`.`address`        AS `delivery_address`,
       `d`.`post`           AS `delivery_post`,
       `ds`.`name`          AS `delivery_status`,
       `o`.`order_date`     AS `order_date`,
       `os`.`name`          AS `order_status`,
       `c`.`id`             AS `cart_id`,
       `c`.`order_item`     AS `order_item`,
       `c`.`order_price`    AS `order_price`,
       `c`.`count`          AS `count`,
       `c`.`origin_price`   AS `origin_price`,
       `c`.`discount_price` AS `discount_price`,
       `c`.`coupon_check`   AS `coupon_check`,
       `i`.`name`           AS `item_name`
from ((((((`pethub`.`order` `o` join `pethub`.`member` `m`
           on ((`o`.`member_id` = `m`.`id`))) join `pethub`.`delivery` `d`
          on ((`o`.`delivery_id` = `d`.`id`))) join `pethub`.`delivery_status` `ds`
         on ((`d`.`status_id` = `ds`.`id`))) join `pethub`.`order_status` `os`
        on ((`o`.`order_status` = `os`.`id`))) join `pethub`.`cart` `c`
       on ((`o`.`id` = `c`.`order_id`))) join `pethub`.`item` `i` on ((`c`.`order_item` = `i`.`id`)));


create definer = root@`%` view reply_view as
select `m`.`nick`      AS `nick`,
       `r`.`id`        AS `id`,
       `r`.`member_id` AS `member_id`,
       `r`.`board_id`  AS `board_id`,
       `r`.`contents`  AS `contents`,
       `r`.`w_date`    AS `w_date`
from (`pethub`.`reply` `r` join `pethub`.`member` `m` on ((`r`.`member_id` = `m`.`id`)));
