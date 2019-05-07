# python 学习总结

1. python使用smtp协议发送邮件的一些坑
    * 不同的邮件服务器会有不同的规定，qq邮箱会要求启用该邮箱的smtp服务，并通过授权码作为密码登录(该授权码一般用于登录第三方客户端, 比如简信,程序也可以看做第三方客户端)。
    * smtp默认端口为25，启用ssl时端口可用465，587。
    * qq邮箱发送到其他服务器的邮件需要使用端口， 且启用TLS。
    * 发送附件即可使用MIMEMultipart，
    邮件正文和附件都可以看做邮件内容的一部分，先创建每一部分，再分别attach到邮件上。
    * 使用163邮箱会有个很坑的地方, msg["To"]和sendmail的函数参数reciver必须一样，否则会报554错误，判断为不垃圾邮件，从而发送失败。如此设定，群发时怎么一一对应是个问题(手动循环群发除外)

2. python中非常有趣或者有用的模块
    * wxpy，通过构建一个类似web微信的机器人对象，可以实现对微信的各种操作，比如和好友聊天，加入群聊等。

    * requests，最好用的http(s)请求库, 支持各种方法，以及构造复杂请求参数，甚至可以完美作为程序中的postman

    * pandas, pandas支持对各种数据文件的(csv, xlsx等等)读取,以及数据对象的各种操作分析， 比起csv,xlrd等模块更好用。
    
    * wordcloud，可以根据文本或者词频生成词云,支持各种样式和设置和背景。
    
    * jieba， 一个好用的nlp库， 可用于分词，准确率极高。
    
    * xpinyin 可以将汉字转为拼音, 使用"-"分隔
    
    * MyQR 可以简单的生成个性化二维码

3. python中print和在交互式环境下变量名的区别  
    print是python中的打印函数， 会对转移字符做处理，比如\b就会让光标回退一格再继续打印后面的值，再交互式环境中，直接使用变量名输出结果，输出的是这个变量存储在计算机中的值而不是打印出来的结果， 而\b的ASCII码值应该就是\x08,  

    ![print和变量值得差异](../img/diff.jpg)
    
4. python下载文件
    ```python
   from urllib.request import urlretrieve
   # python2 from urllib import urlretrieve
   download_url = "https://www.baidu.com/img/bd_logo1.png"
   file_path = "../img/baidu.png"
   urlretrieve(download_url, file_path)

    ```
    
5. python网址编码
    ```python
   from urllib.parse import quote
   keyword = "周杰伦"
   params = quote(keyword.encode("utf-8"))
   search_url = "https://y.qq.com/portal/search.html#page=1&searchid=1&remoteplace=txt.yqq.top&t=song&w={}".format(params)
   # requests请求会默认对参数进行urlencode, 所以不要传递urlencode过的参数给requests请求

    ```