# MyZhihu

## Declaritions 申明
API 来源于IzzyLeung的[知乎日报-API-分析](https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90)  
**本App只为练习使用，不做商业用途。若有侵权，敬请告知，本人将立即删除本Project。**

## OverView 概述
Another **Zhihu Daily app**, it is an MVP practice, and it's my first network type app, 
I will do more efforts on it. If you have good suggesstions, please let me know or fork, star, pull request.      

这是另外一个**知乎日报**实现，貌似最近知乎日报第三方有点多哈，但这是我第一个网络类型的app。
之前从未做过网络类的app，不过这次抱着学习的心态，以及跟上Android的最新开发动态，创建了这个App。
如果你有更好的建议，欢迎提出，或者fork，star，pull request。

本App采用MVP实现，仿照Google AOSP的InCallUi中的MVP样式设计。  
网络框架采用Volley，JSON解析库采用Alibaba的fastjson。  
ViewBinding使用ButterKnife。  
未来还将采用更多的新技术，努力让其更cool。

## Structure 结构

I use Activity as view, and design a corresponding view interface and presenter for it.  
The view interface will make me interface-oriented program and decouple the view, model, controller.  

Model is various bean, which is used to represent a response of a request.  
All the model is updated or assigned values by Volley Network framework.  
It is a very easy-used and friendly library.  

以Activity作为View，并设计相应的view interface，presenter。View interface解耦了view，model以及controller，使得app更容易维护，
各模块相对独立。所谓面向接口编程是也。  
Model由各种各样的Bean组成，它根据网络请求响应生成。不得不说Volley真的很好用。

## Perview 预览

<img src="https://github.com/KylinGu/MyZhihu/blob/master/screenshot/device-2016-03-14-004150.png" alt="GitHub" title="Index" height="400"/>
<img src="https://github.com/KylinGu/MyZhihu/blob/master/screenshot/device-2016-03-16-114236.png" alt="GitHub" title="DetailStory" height="400"/>
<img src="https://github.com/KylinGu/MyZhihu/blob/master/screenshot/device-2016-03-14-004633.png" alt="GitHub" title="DetailStory" height="400"/>

## ToDo 待完成
There's a lot of things to do. 尚有很多新奇的炫酷的东西待加入。
- 1. Abstract a base view interface **Done/完成**
- 2. Add top stories gallery. **Done/完成**
- 3. ReImplemention the detail story. **Almostly/即将完成**
- 4. Using Dagger2 to find view.
- 5. Design a abstract presenter using Generic Type.  **Done/完成**
- 6. Settings.
- 7. Pull up to loading previous stories.
- 8. Using ButterKnife to bind view. **Done/完成**


