# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.sadad.doctor-appointment' is invalid and this project uses 'com.sadad.doctorappointment' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.0/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring cache abstraction](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-caching)
* [Spring Data Redis (Access+Driver)](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-redis)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.7.0/reference/htmlsingle/#boot-features-security)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Caching Data with Spring](https://spring.io/guides/gs/caching/)
* [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

### Task 

داستان: دکتر زمان‌های باز را اضافه می‌کند.
به‌عنوان یک دکتر، دوست دارم که برای هر روز، یک زمان شروع و پایان وارد کنم تا این زمان به بازه‌های ۳۰ دقیقه‌ای تقسیم شود.
اگر یکی از بازه‌ها کمتر از ۳۰ دقیقه شد، نادیده گرفته شود.

موارد تست:

1. [x] اگر دکتر زمان پایان را زودتر از زمان شروع وارد کند، باید خطای مناسبی نمایش داده شود.
2. [x] اگر دکتر زمان شروع و پایان را به‌گونه‌ای وارد کند که بازه زمانی کمتر از ۳۰ دقیقه شود، هیچ زمانی نباید اضافه شود.

**داستان**: دکتر می‌تواند قرارهای ۳۰ دقیقه‌ای را مشاهده کند.
به‌عنوان یک دکتر، دوست دارم قرارهای باز (توسط بیمار گرفته نشده) و گرفته‌شده‌ی ۳۰ دقیقه‌ای را ببینم.

موارد تست:

1. [x] اگر هیچ قراری تنظیم نشده باشد، باید لیست خالی نمایش داده شود.
2. [ ] اگر قرارهایی گرفته شده باشد، باید شماره تلفن و نام بیمار نیز نمایش داده شود.

**داستان**: دکتر می‌تواند قرار باز را حذف کند.
به‌عنوان یک دکتر، دوست دارم بتوانم برخی از قرارهای بازم را حذف کنم.

موارد تست:

1. [ ] اگر هیچ قرار بازی وجود نداشته باشد، خطای ۴۰۴ نمایش داده شود.
2. [ ] اگر قرار توسط بیمار گرفته شده باشد، خطای ۴۰۶ نمایش داده شود.
3. [ ] بررسی همزمانی؛ اگر دکتر در حال حذف قرار باشد و در همان لحظه بیمار در حال گرفتن آن قرار باشد.
4. [ ] 
**داستان**: بیماران می‌توانند قرارهای باز دکتر را مشاهده کنند.
به‌عنوان یک بیمار، دوست دارم بتوانم تمام قرارهای باز دکتر را برای روز مشخص مشاهده کنم تا یکی از آنها را بگیرم.

موارد تست:

1. [ ] اگر دکتر در آن روز هیچ قرار بازی نداشته باشد، باید لیست خالی نمایش داده شود.
2. [ ] 
**داستان**: بیماران می‌توانند یک قرار باز را بگیرند.
به‌عنوان یک بیمار، دوست دارم بتوانم یک قرار باز را با وارد کردن نام و شماره تلفنم بگیرم.

موارد تست:

1. [ ] اگر شماره تلفن یا نام وارد نشود، باید پیغام خطای مناسبی نمایش داده شود.
2. [ ] اگر قرار قبلاً گرفته شده یا حذف شده باشد، باید پیغام خطای مناسبی نمایش داده شود.
3. [ ] بررسی همزمانی؛ بیمار در حال گرفتن قراری است که در حال حذف یا توسط بیمار دیگری گرفته می‌شود.

**داستان**: بیماران می‌توانند قرارهای خود را مشاهده کنند.
به‌عنوان یک بیمار، دوست دارم بتوانم قرارهای خودم را با وارد کردن فقط شماره تلفنم مشاهده کنم.

موارد تست:

1. [ ] اگر هیچ قراری با این شماره تلفن وجود نداشته باشد، لیست خالی نمایش داده شود.
2. [ ] اگر بیش از یک قرار توسط این کاربر گرفته شده باشد، همه قرارها باید نمایش داده شود.