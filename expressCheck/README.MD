# Java抓取快递数据
*感谢指导贴的帮助[Java抓取网页数据（原网页+Javascript返回数据）](http://blog.csdn.net/zgyulongfei/article/details/7909006)*
    快递查询功能总结：（由于快递API Url需要收费，而且还有限次数，工作需要，所以利用快递网的跳转API爬取数据）
    1.选择跳转API的原因：
        (1)免费，不需要申请授权key，可以直接使用
        (2)无使用次数限制
        (3)稳定性最强
        (4)支持所有快递公司
    2.遇到的问题：
        (1)网站为了保护自己的数据，没有把数据直接放在网页源码中返回，而是采用异步的方式，用JS返回数据，此时需要工具获取数据
        (2)刚开始使用快递100的跳转API，结果发现虽然理论上无限制，但到一定次数后就无法查询有一段时间不能使用
        (3)获取过程中发现申通快递返回的数据带有a标签，Jsoup不能正确解析，所以需要进行一定处理
    3.抓取过程：
        (1)利用HTTP Analyzer工具截获http的交互内容，截取内容例 http://www.kuaidi.com/index-ajaxselectcourierinfo-227508615865-shentong.html
        (2)由截获的数据可知 http://www.kuaidi.com/index-ajaxselectcourierinfo-快递单号-快递公司名.html 。其中快递公司名一般为公司名的拼音
    4.功能实现：（前端显示，后台编写java提供接口）
