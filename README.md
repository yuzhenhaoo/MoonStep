## 圆月App

### descriptipon

圆月App致力于打造一种全新的社交性领域，在多样功能性的基础上，增加几大相互嵌套的体系，致力于改善当今人们生活中出现的无聊情绪，独特的地图搜寻体系，奖励体系，权限体系相互嵌套，构筑完美圆月世界。

###　程序界面

- 引导页

![](https://i.imgur.com/h68ufIw.jpg)

![](https://i.imgur.com/lF8GOis.jpg)

![](https://i.imgur.com/vqgwvFa.jpg)

![](https://i.imgur.com/SVLoGXD.jpg)

![](https://i.imgur.com/bCNB2eN.png)

![](https://i.imgur.com/kLNv1u3.png)

- 登录界面

![](https://i.imgur.com/HeacFrM.png)

- 侧滑栏

![](https://i.imgur.com/rwCj7VO.png)

### 项目开发流程

#### 产品需求

**需求分析**

> 需求了解

> 功能划分
	
> 技术思考
	
	- 采用MVP的架构模式，使逻辑和View层高度欠耦合，一方面增强可读性，一方面让每个单独的模块可以单独的提取出来进行测试。
 	- 注册的方式采用手机注册，原本设计了邮箱注册的页面和功能，可是github上也没有好用的API，自己写的话，不开服务器又只能用自己的邮箱账号，开了服务器维护和操作都较麻烦，所以取消该功能。
#### 交互设计

> 方案选择
	
  从根本上，打造全中国独一无二的游戏型社交类APP,多重体系相互嵌套，构建完美社交app，纠正当今青年人的畸形娱乐观。

> 结构设计
   
  五大种族，七大体系，相互嵌套，各种完善的系统，优质多样化的玩法，更是衡量于游戏和社交二者的平衡性能，多重考虑，用完善体系铸就骨架，用心打造灵魂，用极致UI构建外在。

> 详细设计
	
  详细内容见设计文档。
	
> 设计文档
	
  ![]()

#### 视觉设计

(UI界面设计好后从设计的灵感上来阐述这一部分的内容)

#### 开发
- 编码

	UTF-8

- 框架搭建

	fanLayoutManager 多样式LayoutManager扩展框架

	ViewPager 

	CircleImageView 圆形头像框架

	recycleView 

	JellyToggleButton 流动按钮

	CarouselLayoutManager

	PullToRefreshView 下拉刷新框架

 	Butterknife 黄油刀

	ENViews 动态加载框架

	HTextView 自定义动态效果的TextView

	Glide 图片加载框架

	LitePal(数据库框架)

	Mob的短信 sdk

	百度map sdk

- 帐号申请
- 官网运营
- 代码混淆
- 优化重构
- 开发工具
   
	Android Stdio
   
	Tomcat

	NetBeans 
	
	MySQL

	PostMan

	PhotoShop CC 2018

UI设计工具：墨刀

#### 测试

- 视觉测试
- 交互测试
- 产品测试

#### 发布

#### 开发流程一览

7/31/2018

第一次开创项目，定下立项，构建目标，利用工具开始打造圆月UI界面。

8/4/2018 

墨刀打造UI界面基本完成，未进行效果优化，开始编写结构代码。

8/12/2018

引导页+登录页面基本完成，引导页使用了Fragment来便于管理生命周期，登录页面进行优化，注册页面完成，注册功能尚未完善。

8/15/2018 

主页面架构大体完成，利用Fragment嵌套和切换，实现侧滑菜单中的菜单项共联。

8/18/2018

处理完新一轮的bug,菜单栏第三项已经完成，采用FanLayoutManager的新框架，打造风车布局recyclerView，优化界面，并在其中完成了多重fragment的设计和切换。

8/21/2018

客服页面完成，还是调用的RecyclerView的多重item模式，数据的传输尚未完成，因为客服交互的平台尚未搭建好，预计未来会使用网络+数据解析的方式完成数据的传递。

8/23/2018

背包界面完成，采用网格布局，内置功能尚未完成，方格采用ImageView的组件实现，物品栏目使用前景进行填充，并对每个item进行监控，点击后进行弹窗，尚未进行设置。

2018/9/6

解决Butterknife与android 3.0冲突的问题，成功使用Butterknife Zeleny插件，减少了每个页面要写findViewById()的冗杂。

2018/9/7 

使用Mob平台的短信SDK，动态架构进需要使用的gradle配置层，并且在登陆页面实现手机验证的功能。

2018/9/16-22

使用mvp架构模式整改android文件架构，将所有文件区分为区块-逻辑块(Module) + Presenter(桥街块) + View（视图）三块
实现逻辑和视图的高度欠耦合。 

2018/9/21

使用了移位动画，改进所有按钮点击的效果，不再通过来回的改变TextView的Size大小或者改变button本身的颜色来达到点击效果，而是通过button的晃动效果实现用户的优良体验。

2018/9/25

Login-MVP架构模式完全完成，成功掌握了MVP架构模式地精髓，并具有了一定地架构能力和经验，并对MVP地三个层次分别有了自己地认识和了解，可以自由地掌控MVP地架构方法。

2018/10/1

开始接入环信的SDK服务，实现项目内容第一块的社交块。

2018/10/12

消息接收流程尚在处理，对于用户大量网络数据的保存计划集成在本地的sqlite数据库中，计划使用开源库:GreenDao数据库框架对用户的好友等信息进行保存，理由是每次从网络上获取好友的信息太过耗费时间，也耗费流量，极大程度的降低了用户的体验度。

#### Bug流程一览

- 8/10/2018

Bug类型：`OOM + Launcher3 has stopped`

Bug原因：Icon设置大小未调节

Bug解决时间： 8/12/2018

- 8/17/2018

Bug类型：`没有报错+fragment切换失效`

Bug原因：主fragment向activity建立依赖not work,原因是没有调用一个自定义的`newInstance`的实例。

Bug解决时间： 8/18/2018

- 8/19/2018 

Bug类型：`称号详情页面无法去除来自主activity的标题栏`

Bug原因：称号页面由单独的fragment通过 replace的方法来产生，然而，也正是在这样的情况下，它的产生于否取决于用户是否对某一称号进行点击，如果点击的话，才会决定该Fragment的生成，如果要在一个activity中去隐藏当前的标题栏，那是一个很容易的事情，但是如果在一个后来生成的单独的Fragment中做到这一点，那几乎是无法完成的事情，因为由于调用顺序的原因，会爆出一个RunTimeException，原因是setFeature()的顺序必须排在adding Content的前面，前后两者出现逻辑矛盾，几乎不可实现。（当前想法：对fragment和activity进行通信，如果fragment产生的时候，在activity中接收讯息，然后对其进行标题栏的隐藏，关闭的时候在监听，进行标题栏的显示，尚未实行。）

Bug解决时间： 2018/8/20

- 8/21/2018

Bug类型：`Android字体切换失效`

Bug原因：欲切换界面字体，采用缓冲区的方式对字体进行缓冲，然而不能修改字体。

Bug解决时间：尚未解决

- 8/24/2018

Bug类型：`PullToRefreshView一直刷新无法取消`

Bug原因：进入社交页面，PullToRefreshView一直在刷新，无法取消刷新状态，调试也无法进入相应的触点函数。

Bug解决时间：8/25/2018

- 2018/9/22

Bug类型：`button中的setOnClick中设置耗时操作时，刷新页面无法正常启动`

Bug原因：在button的setOnclick函数中先开启了Loading框，然后通过一个new Thread()中进行耗时操作，再在new Thread()完成后，实现Loading框的取消，但是这里没有考虑到new Thread()包括AsnyckTask都是异步的操作，所以这里的开启Loading和取消Loading的操作实际上是相当于上下衔接的，之间没有间隔时间，所以看起来就是Loading没有开启的效果。便想到了第二种方式：通过Thread.currentThread().sleep(time);模拟耗时的操作，直接让当前线程沉睡，但是又导致了微弱的ANR，因为Button的响应速度非常缓慢，导致Loading的开启只有非常短暂的时间，实则也不可取。

解决方案：这个问题比较特殊，所以这里贴上解决方案，直接通过setOnTouch()方法来代替原本的setOnClick()方法，直接在其中捕捉button的按下和弹起两个基本事件，我们在button按下的一瞬间显示Loading加载框，在button弹起的一瞬间开启网络操作，并在这个时候使用上面第二个方法中的Thread.currentThread().sleep(time);让当前主线程来休眠一个不超过5s的时间，这个时候button已经被点击完毕了，所以微弱的ANR不会对button本身产生影响，问题成功解决。

Bug解决时间：2018/9/22 

- 9/24/2018

Bug类型：`网络请求完毕后，没有相应地用户提示，并且activity异常崩溃，由于输出管道地损坏，没有报错信息地显示`

Bug原因：在Presenter的事件监听中，不能直接在子Thread中使用Toast的方法，必须新创建一个子线程，然后才能在子线程中调用Toast方法，注意要给子线程一个Handler地循环机制。

Bug解决时间：9/25/2018

- 2018/10/3

Bug类型：`在使用环信的获取好友列表的功能的时候，返回错误:Unknown_server_error，无法正常获取当前用户的好友列表`

Bug解决措施：只需要将对应的ECAPI放入一个子线程中就可以完成对于当前用户好友列表的获取了，具体原因不明。

Bug解决时间：2018/10/4

- 2018/10/4

Bug类型：`在Application中查找并向MoonStep_Palace中添加User对象的时候，由于是网络请求的缘故，传给List的数据会很慢，这样的话，Recycler中地adapter会逐渐刷新界面，然而，由于List尚未完全完成对于数据地请求操作,所以此时看来RecyclerView中地数据只能显示一部分，处理地方式是什么呢？`

Bug解决措施：已经解决，解决方式：在第一次加载的时候，将好友的信息存储到SQLite中，一方面减少对网络请求的依赖，递增速度，第二个是在好友页面直接从本地数据库中加载的话，比从网络上重新加载要快了许多。

Bug解决时间：2018/10/12