# logicer
- Rebuild of mylog
- Dubbo or other rpc framework available
- Clear structure with dependencies
## modules
* Modules:
  * blog-api
  * blog-server
    * blog相关服务
  * chat-api
  * chat-server
    * netty服务聊天客户端
  * file-api
  * licence-api
  * licence-server
    * 权限认证服务
  * comm-server
    * netty服务
  * framework-model
    * 框架通用实体对象
  * framework-web   
    * 框架扩展，包括一些工具类和自定义组件
  * logicer-base
    * 自定义通信协议，通用组件、工具等
## Features
### 1.0.0 Finished
- 完成重构，各服务可正常部署
- 服务启动必要信息不再于nacos维护，改为本地配置，nacos后续仅作为动态配置之用
### 1.0.1 Todo
- 梳理内部调用逻辑，修改统一鉴权、文件管理等基础服务的调用方式为dubbo调用
- 进一步梳理pom中的依赖