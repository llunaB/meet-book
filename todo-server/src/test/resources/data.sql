INSERT INTO conference (
id,
user_id,
book_id,
call_start_time,
call_end_time,
question,
password,
thumbnail_url,
title,
description,
max_members,
tags
)
VALUES (1, 1, 2, '2016-11-22 12:34:56.7', '2016-11-22 12:34:56.7', '질문', '대답', 'url.of.image/conference_id', '제목', '회의', 8, '#tag');

INSERT INTO conference (
    id,
    user_id,
    book_id,
    call_start_time,
    call_end_time,
    question,
    password,
    thumbnail_url,
    title,
    description,
    max_members,
    tags
)
VALUES (2, 4, 5, '2016-11-22 12:34:56.7', '2016-11-22 12:34:56.7', '질문2', '대답2', 'url.of.image/conference_id', '제목', '회의', 8, '#tag');

INSERT INTO conference (
    id,
    user_id,
    book_id,
    call_start_time,
    call_end_time,
    question,
    password,
    thumbnail_url,
    title,
    description,
    max_members,
    tags
)
VALUES (3, 1, 4, '2016-11-22 12:34:56.7', '2016-11-22 12:34:56.7', '질문3', '대답3', 'url.of.image/conference_id', '제목', '회의', 8, '#tag');

INSERT INTO USER(
    id,
    name,
    password,
    nickname,
    email,
    gender,
    age,
    host_point,
    guest_point,
    profile_image,
    profile_description
)
VALUES (1, '이름1', '비번1', '별명1', '이메일1', 0, 20, 0, 0,'경로', '프로필설명');
;

INSERT INTO USER(
    id,
    name,
    password,
    nickname,
    email,
    gender,
    age,
    host_point,
    guest_point,
    profile_image,
    profile_description
)
VALUES (2, '이름2', '비번2', '별명2', '이메일2', 0, 20, 0, 0,'경로', '프로필설명');

