# Database Exercise
# Use SOURCE command to run this script

DROP DATABASE TIMEDB;
CREATE DATABASE TIMEDB;
USE TIMEDB;

CREATE TABLE COURSE (
COURSE_ID VARCHAR (20),
TITLE VARCHAR (30),
MAJOR VARCHAR (20),
GRADE NUMERIC(1,0),
SEMESTER INTEGER,
CREDIT NUMERIC(1,0),
INSTRUCTOR VARCHAR(30), 
PRIMARY KEY (COURSE_ID));

CREATE TABLE COURSE (
COURSE_ID VARCHAR(20),
TIME NUMERIC(2,1),
PRIMARY KEY(COURSE_ID),
FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID));

INSERT INTO COURSE VALUES ('09392001', '이산수학', '전필', 1, 1, 3, '황효석');
INSERT INTO COURSE VALUES ('09392002', '이산수학', '전필', 1, 1, 3, '황효석');
INSERT INTO COURSE VALUES ('09392003', '이산수학', '전필', 1, 1, 3, '이주형');
INSERT INTO COURSE VALUES ('09656001', '웹 프로그래밍', '전필', 1, 1, 3, '강상우');
INSERT INTO COURSE VALUES ('09656002', '웹 프로그래밍', '전필', 1, 1, 3, '이상웅');
INSERT INTO COURSE VALUES ('09656003', '웹 프로그래밍', '전필', 1, 1, 3, '강상우');
INSERT INTO COURSE VALUES ('09656004', '웹 프로그래밍', '전필', 1, 1, 3, '이상웅');
INSERT INTO COURSE VALUES ('09656005', '웹 프로그래밍', '전필', 1, 1, 3, '민연아');
INSERT INTO COURSE VALUES ('11672001', '컴퓨터 프로그래밍', '전필', 1, 1, 3, '최아영');
INSERT INTO COURSE VALUES ('11672002', '컴퓨터 프로그래밍', '전필', 1, 1, 3, '정용주');
INSERT INTO COURSE VALUES ('11672003', '컴퓨터 프로그래밍', '전필', 1, 1, 3, '최아영');
INSERT INTO COURSE VALUES ('11672004', '컴퓨터 프로그래밍', '전필', 1, 1, 3, '정용주');
INSERT INTO COURSE VALUES ('11672005', '컴퓨터 프로그래밍', '전필', 1, 1, 3, '민연아');
INSERT INTO COURSE VALUES ('06440001', '자료구조', '전필', 2, 1, 3, '김원');
INSERT INTO COURSE VALUES ('06440002', '자료구조', '전필', 2, 1, 3, '민연아');
INSERT INTO COURSE VALUES ('06440003', '자료구조', '전필', 2, 1, 3, '민연아');
INSERT INTO COURSE VALUES ('09805001', '객체지향 프로그래밍', '전필', 2, 1, 3, '노웅기');
INSERT INTO COURSE VALUES ('09805002', '객체지향 프로그래밍', '전필', 2, 1, 3, '노웅기');
INSERT INTO COURSE VALUES ('09805003', '객체지향 프로그래밍', '전필', 2, 1, 3, '최아영');
INSERT INTO COURSE VALUES ('09806001', '운영체제', '전필', 2, 1, 3, '황효석');
INSERT INTO COURSE VALUES ('09806002', '운영체제', '전필', 2, 1, 3, '유준');
INSERT INTO COURSE VALUES ('09806003', '운영체제', '전필', 2, 1, 3, '유준');
INSERT INTO COURSE VALUES ('12425003', '에듀에코세미나1', '전선', 2, 1, 1, '정용주');
INSERT INTO COURSE VALUES ('09836001', '경제학개론', '전선', 2, 1, 2, '서미숙');
INSERT INTO COURSE VALUES ('09836002', '경제학개론', '전선', 2, 1, 2, '서미숙');
INSERT INTO COURSE VALUES ('10216016', '건축문화사', '교선', 3, 1, 2, '최동혁');
INSERT INTO COURSE VALUES ('10176001', '컴퓨터 그래픽스', '전필', 3, 1, 3, '최진우');
INSERT INTO COURSE VALUES ('10176002', '컴퓨터 그래픽스', '전필', 3, 1, 3, '최진우');
INSERT INTO COURSE VALUES ('10177001', '소프트웨어공학', '전필', 3, 1, 3, '정옥란');
INSERT INTO COURSE VALUES ('10177002', '소프트웨어공학', '전필', 3, 1, 3, '정옥란');
INSERT INTO COURSE VALUES ('10178001', '모바일 프로그래밍', '전필', 3, 1, 3, '이주형');
INSERT INTO COURSE VALUES ('10178002', '모바일 프로그래밍', '전필', 3, 1, 3, '이주형');
INSERT INTO COURSE VALUES ('10375001', '경영학원론', '전필', 3, 1, 3, '김학진');
INSERT INTO COURSE VALUES ('10375002', '경영학원론', '전필', 3, 1, 3, '김학진');
INSERT INTO COURSE VALUES ('11852001', '졸업작품1', '전필', 3, 1, 1, '이주형');
INSERT INTO COURSE VALUES ('11853001', '소프트웨어 산업세미나', '전필', 3, 1, 2, '김원');
INSERT INTO COURSE VALUES ('06480001', '데이터마이닝', '전필', 4, 1, 3, '강상우');
INSERT INTO COURSE VALUES ('09031001', '멀티미디어 및 실습', '전필', 4, 1, 3, '정용주');
INSERT INTO COURSE VALUES ('06480001', '기술경영', '전필', 4, 1, 3, '김홍섭');
INSERT INTO COURSE VALUES ('06480001', '줄업작품3', '전필', 4, 1, 2, '강상우');
INSERT INTO COURSE VALUES ('06480001', '소프트웨어 신기술특론', '전선', 4, 1, 3, '정용주');
INSERT INTO COURSE VALUES ('11393022', '생명과 나눔', '교선', 1, 2, 2, '홍을표');
INSERT INTO COURSE VALUES ('11670087', '한국사', '교선', 1, 2, 2, '김윤정');
INSERT INTO COURSE VALUES ('12408067', '가천리더십', '교선', 1, 2, 2, '송진구');
INSERT INTO COURSE VALUES ('11365013', '창의적 고전읽기', '교선', 1, 2, 2, '박지해');
INSERT INTO COURSE VALUES ('11366047', '역사와 상상력', '교선', 1, 2, 2, '안윤아');
INSERT INTO COURSE VALUES ('11371013', '창조와 몰입', '교선', 1, 2, 2, '양국현');
INSERT INTO COURSE VALUES ('12406047', '논리적 사고', '교선', 1, 2, 2, '김태량');
INSERT INTO COURSE VALUES ('12407013', '사고와 표현', '교선', 1, 2, 2, '류진희');
INSERT INTO COURSE VALUES ('12410013', '창의적 사고', '교선', 1, 2, 2, NULL);
INSERT INTO COURSE VALUES ('09377003', '글로벌시대의 리더십론', '교선', 1, 2, 2, '김원');
INSERT INTO COURSE VALUES ('09377004', '글로벌시대의 리더십론', '교선', 1, 2, 2, '김원');
INSERT INTO COURSE VALUES ('11963001', '소프트웨어의 생태계', '교선', 1, 2, 2, '김진환');
INSERT INTO COURSE VALUES ('11963002', '소프트웨어의 생태계', '교선', 1, 2, 2, '송근실');
INSERT INTO COURSE VALUES ('11388017', '과학기술글쓰기', '교선', 1, 2, 2, '박현선');
INSERT INTO COURSE VALUES ('11388018', '과학기술글쓰기', '교선', 1, 2, 2, '박성지');
INSERT INTO COURSE VALUES ('10372001', '로봇공학', '전필', 1, 2, 3, '민연아');
INSERT INTO COURSE VALUES ('10372001', '로봇공학', '전필', 1, 2, 3, '민연아');
INSERT INTO COURSE VALUES ('10372002', '로봇공학', '전필', 1, 2, 3, '정용주');
INSERT INTO COURSE VALUES ('10372003', '로봇공학', '전필', 1, 2, 3, '강상우');
INSERT INTO COURSE VALUES ('10372004', '로봇공학', '전필', 1, 2, 3, '민연아');
INSERT INTO COURSE VALUES ('10372005', '로봇공학', '전필', 1, 2, 3, '정용주');
INSERT INTO COURSE VALUES ('10372006', '로봇공학', '전필', 1, 2, 3, '강상우');
INSERT INTO COURSE VALUES ('10380001', '확률통계', '전필', 1, 2, 3, '노웅기');
INSERT INTO COURSE VALUES ('10380002', '확률통계', '전필', 1, 2, 3, '최아영');
INSERT INTO COURSE VALUES ('10380003', '확률통계', '전필', 1, 2, 3, '최아영');
INSERT INTO COURSE VALUES ('11494001', '소프트웨어구현패턴', '전필', 1, 2, 3, '황효석');
INSERT INTO COURSE VALUES ('11494002', '소프트웨어구현패턴', '전필', 1, 2, 3, '이주형');
INSERT INTO COURSE VALUES ('11494003', '소프트웨어구현패턴', '전필', 1, 2, 3, '민연아');
INSERT INTO COURSE VALUES ('11494004', '소프트웨어구현패턴', '전필', 1, 2, 3, '이주형');
INSERT INTO COURSE VALUES ('05309010', '중국어2', '교선', 2, 2, 2, '김민경');
INSERT INTO COURSE VALUES ('12427032', '사회봉사', '교필', 2, 2, 0, '유준');
INSERT INTO COURSE VALUES ('09968001', '알고리즘', '전필', 2, 2, 3, '정옥란');
INSERT INTO COURSE VALUES ('09968002', '알고리즘', '전필', 2, 2, 3, '정옥란');
INSERT INTO COURSE VALUES ('09968003', '알고리즘', '전필', 2, 2, 3, '이상웅');
INSERT INTO COURSE VALUES ('09969001', '데이터베이스 및 실습', '전필', 2, 2, 3, '노웅기');
INSERT INTO COURSE VALUES ('09969002', '데이터베이스 및 실습', '전필', 2, 2, 3, '민연아');
INSERT INTO COURSE VALUES ('09969003', '데이터베이스 및 실습', '전필', 2, 2, 3, '노웅기');
INSERT INTO COURSE VALUES ('09970001', '컴퓨터네트워크 및 실습', '전필', 2, 2, 3, '유준');
INSERT INTO COURSE VALUES ('09970002', '컴퓨터네트워크 및 실습', '전필', 2, 2, 3, '유준');
INSERT INTO COURSE VALUES ('09970003', '컴퓨터네트워크 및 실습', '전필', 2, 2, 3, '이주형');
INSERT INTO COURSE VALUES ('12739001', 'IT와 창업', '전선', 2, 2, 2, '이상순');
INSERT INTO COURSE VALUES ('11357032', '진로세미나', '교필', 3, 2, 1, '유준');
INSERT INTO COURSE VALUES ('11357154', '진로세미나', '교필', 3, 2, 1, '노웅기');
INSERT INTO COURSE VALUES ('11357155', '진로세미나', '교필', 3, 2, 1, '정옥란');
INSERT INTO COURSE VALUES ('11357156', '진로세미나', '교필', 3, 2, 1, '정용주');
INSERT INTO COURSE VALUES ('11357157', '진로세미나', '교필', 3, 2, 1, '최아영');
INSERT INTO COURSE VALUES ('11357158', '진로세미나', '교필', 3, 2, 1, '이상웅');
INSERT INTO COURSE VALUES ('11357159', '진로세미나', '교필', 3, 2, 1, '이주형');
INSERT INTO COURSE VALUES ('11357160', '진로세미나', '교필', 3, 2, 1, '황효석');
INSERT INTO COURSE VALUES ('11393034', '생명과 나눔', '교필', 3, 2, 2, '김정수');
INSERT INTO COURSE VALUES ('08287001', '분산시스템', '전필', 3, 2, 3, '황효석');
INSERT INTO COURSE VALUES ('09571001', '컴퓨터구조', '전필', 3, 2, 3, '정옥란');
INSERT INTO COURSE VALUES ('10184001', '마케팅', '전필', 3, 2, 3, '김학진');
INSERT INTO COURSE VALUES ('11243001', '고급 웹 프로그래밍', '전필', 3, 2, 3, '이상웅');
INSERT INTO COURSE VALUES ('11243002', '고급 웹 프로그래밍', '전필', 3, 2, 3, '이상웅');
INSERT INTO COURSE VALUES ('11260001', '졸업작품2', '전필', 3, 2, 3, '이주형');
INSERT INTO COURSE VALUES ('12739002', 'IT와 창업', '전필', 3, 2, 2, '이상순');
INSERT INTO COURSE VALUES ('06493001', '컴퓨터보안', '전필', 4, 2, 3, '이상웅');
INSERT INTO COURSE VALUES ('11258001', 'HCI', '전필', 4, 2, 3, '최아영');
INSERT INTO COURSE VALUES ('12221001', '기업가정신', '전필', 4, 2, 3, '이호승');
INSERT INTO COURSE VALUES ('12739003', 'IT와 창업', '전필', 4, 2, 2, '이상순');

COMMIT;
