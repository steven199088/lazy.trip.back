# Lazy Trip

## 關於

緯育 TGA105 第 3 組專題製作，網站後端部分。  
前端：[https://github.com/JunliXiao/lazy-trip-front](https://github.com/JunliXiao/lazy-trip-front)  
Demo：[http://35.194.214.76/lazy-trip-back/](http://35.194.214.76/lazy-trip-back/) (2023/5 起將關閉)

## 分工

瀚賢：會員系統、管理員後台  
奕帆：行程系統、串接 Google Map API  
常富：訂房系統、串接 LinePay + Google Map API  
俊立：好友系統、多人聊天、網站設計風格  
思德：揪團系統、聊天室  
力維：廠商房型系統  
永澄：首頁、推薦文章、客服聊天

## 目錄結構

common 套件存放共同資源，如各類 data source 和 filter。每個人負責之功能，各自對應到一至兩個套件，套件內再至少以 controller /
dao / model / service 的結構細分程式碼的存放。大家應只編輯自己負責的套件。

- main
    - java
        - common (瀚賢、俊立)
        - admin (瀚賢)
        - article、customerService (永澄)
        - company (力維)
        - friend (俊立)
        - group (思德)
        - member (瀚賢)
        - order (常富)
        - tour (奕帆)
    - webapp (lazy-trip-front：前端資源放在這)

## 版本控制

~~main 分支~~  
僅使用 dev 分支。  
每個人每次推 commit 到遠端 dev 分支，即一次進度整合；每次拉 commit 到每個人本地 dev 分支，即一次更新。每次推 commit
時，除非離上次推的時間內沒有別人推過，不然都會需要先 merge 或 rebase 別人推的 commit。在大家沒有更動到別人的負責目錄的前提下，通常不會遇到衝突需要處理。

## Anything Else

Work in progress...