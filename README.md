# Norma Spring Boot Final Project

## Used Technologies & Arhitecture & Methods:
* Monolithic Architecture 
* Java 17
* Spring Boot 
* Spring Security 
* Postgres 
* JWT
* Swagger

## Swagger Endpoint
* http://localhost:8086/swagger-ui/index.html#/

## Fuctions
* Customer Manangement
* User Management
* Account Management
* Card Management
* Tarnsfer Management

## About Project Flow 

The basic working logic of the project is as follows. Customers can create accounts and cards, and connect more than one account to one card, within the framework of certain rules, after the following transactions are carried out.

![bank-flow](img/bank_flow.jpg)


## Database Diagrams

![bank](img/bank.jpg)


## Project Description

Bu bitirme projesi kapsamında bootcamp katılımcılarının bir online bankacılık sisteminin backend servislerini yazmaları 
beklenmektedir. Proje kapsamında beklenen minimum fonksiyonlar ve teknik ihtiyaçlar aşağıda listelenmiştir.

Bitirme projesi bireysel olarak implemente edilecek birbiriyle aynı olan projeler başarısız sayılacaktır. Bunun yanında
yardımlaşma, fikir alışverişi herzamanki gibi desteklenmektedir :) 

## Beklenen fonksiyonlar
* Müşteri yönetimi
* Hesap yönetimi
* Kart yönetimi
* Transfer yönetimi

### Müşteri yönetimi
Yaratılacak API'lar aracılığıyla; müşteri yaratma, güncelleme ve silme işlemleri mümkün olacaktır. Silme işlemi hesaplarında
parası bulunan yada kredi kartı borcu bulunan müşteriler için mümkün olmayacak.

### Hesap yönetimi
Banka müşterilerinin yatırımlarını kontrol etmek amacıyla kullanabilmeleri için hesap yaratmalarına, silmelerine API'lar
aracılığıyla izin verilecektir. Kullanıcılar iki farkılı türde hesap açabilecek, vadesiz mevduat hesabı ve birikim hesabı.
İki hesap arası para transferi yapılabilecek, vadesiz mevduat hesabı başka hesaplara para transferi için kullanılabilecekken
birikim hesabından doğrudan para transferi yapılamayacak. Hesaplar TL, Euro yada Dolar para birimlerinde açılabilecek.

### Kart yönetimi
Müşterilere banka tarafından bankamatiklerde yada alışverişte kullanılmak üzere ön ödemeli banka kartı ve kredi kartları
sunulmaktadır. Bu kartların yaratılması, müşteri ve hesapla ilişkilendirilmesi, kart kullanarak para transferi (alışveriş) 
fonksiyonları API'lar aracılığıyla sağlanacaktır.

Ayrıca kredi kartları için; borç sorgulama, hesaptan borç ödeme, bankamatikten borç ödeme, ekstre görüntüleme (JSON formatında) 
işlemleri yine API aracılığı ile yapılabilecek.

### Transfer yönetimi
Müşterilerin para transferlerini yönetmek için uygun API'lar sağlanmalıdır. Bir müşteri farklı para birimlerinde açılan 
hesaplar arası transfer yapmak isterse güncel para kuru https://api.exchangeratesapi.io/latest?base=TRY API'dan 
alınmalı ve dönüşüm yapılıp transfer öyle gerçekleştirilemeli. Transfer işlemleri sadece IBAN üzerinden gerçekleştirilebilecek.

## Teknik beklentiler
Yukarda belirtilen tüm operasyonlar tamamı Java ve Spring Boot  kullanılarak implemente edilecektir, proje genelinde
bootcamp boyunca öğrendiğimiz OOP prensipleri doğru şekilde uygulanmalıdır. 

Tüm fonksiyonlar REST API'lar aracılığı ile yerine getirilmeli ve API'lar swagger arayüzü ile dökümantasyonu yapılmalıdır.
Hata durumları uygun şekilde ele alınmalı ve hatalara uygun responselar üretilmeli.

Uygulama genelinde Spring Boot, Spring Rest ve JPA kullanılmalı, clean code ve SOLID prensiplerine uyulmalı, unit testler 
eklenmelidir.
