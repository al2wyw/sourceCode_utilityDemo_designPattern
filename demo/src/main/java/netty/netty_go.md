|特性 |	Go语言 |	Netty
|-|-|-|
|实现机制 |	netpoller + epoll/kqueue |	Selector + Channel
|并发模型 |	goroutine + channel |	EventLoop + Thread
|调度方式 |	运行时调度 |	线程池调度
|编程方式 |	同步代码实现异步逻辑 |	回调机制
|连接处理 |	每连接一个goroutine |	每连接一个Channel