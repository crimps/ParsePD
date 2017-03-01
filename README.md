pdm、cdm格式文件是数据库设计软件powerDesigen的文件格式，powerdesigne并没有对应的mac版本，
为了方便查看用java实现了一个简单的查看器ParsePDM，可以查看表、表字段、表主键，
至于有更高需求的用户还是用虚拟机或是win启用powerDesigne。
ParsePDM的主要功能只是查看表、表字段信息，至于其他功能由于目前在实际使用中并没有其他的需求，
因此并未进行开发，后期也不会再进行开发。有更多需求的可以下载源码自行开发。[源码地址][1]
PS:此源码是在smshen代码的基础上添加了读取cdm格式功能，[smshen git地址][2]
软件界面：
![parsePDM界面][image-1]

[1]:	https://github.com/crimps/ParsePDM
[2]:	https://github.com/smshen/ParsePDM

[image-1]:	parsePDM界面.png
