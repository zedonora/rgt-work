-- 사용자 데이터 삽입
INSERT INTO users (username, password, role) VALUES
                                                 ('customer1', '$2a$10$3Vtyfl7osWyY4lc9KB2Go.oNRsy.rc22s4dfxxBErAqefbrJBG3fa', 'CUSTOMER'),
                                                 ('customer2', '$2a$10$3Vtyfl7osWyY4lc9KB2Go.oNRsy.rc22s4dfxxBErAqefbrJBG3fa', 'CUSTOMER'),
                                                 ('owner1', '$2a$10$3Vtyfl7osWyY4lc9KB2Go.oNRsy.rc22s4dfxxBErAqefbrJBG3fa', 'OWNER');

-- 레스토랑 데이터 삽입
INSERT INTO restaurants (name, owner_id) VALUES
                                             ('맛있는 식당', 3),
                                             ('행복한 식당', 3);

-- 테이블 데이터 삽입
INSERT INTO tables (restaurant_id, table_number) VALUES
                                                     (1, 1), (1, 2), (1, 3),
                                                     (2, 1), (2, 2), (2, 3);

-- 메뉴 데이터 삽입
INSERT INTO menus (restaurant_id, name, price, description) VALUES
                                                                (1, '불고기', 15000, '맛있는 한국식 불고기'),
                                                                (1, '비빔밥', 12000, '영양만점 비빔밥'),
                                                                (1, '김치찌개', 10000, '얼큰한 김치찌개'),
                                                                (2, '스테이크', 25000, '부드러운 안심 스테이크'),
                                                                (2, '파스타', 18000, '알덴테 크림 파스타'),
                                                                (2, '샐러드', 8000, '신선한 그린 샐러드');

-- 장바구니 아이템 데이터 삽입 (예시)
INSERT INTO cart_items (user_id, menu_id, quantity) VALUES
                                                        (1, 1, 2),
                                                        (1, 2, 1),
                                                        (2, 4, 1);

-- 주문 데이터 삽입 (예시)
INSERT INTO orders (user_id, table_id, status, created_at) VALUES
                                                               (1, 1, 'CONFIRM', CURRENT_TIMESTAMP),
                                                               (2, 4, 'COMPLETE', DATEADD('HOUR', -1, CURRENT_TIMESTAMP));

-- 주문 아이템 데이터 삽입
INSERT INTO order_items (order_id, menu_id, quantity, price) VALUES
                                                                 (1, 1, 2, 15000),
                                                                 (1, 2, 1, 12000),
                                                                 (2, 4, 1, 25000);