# 更新日志
## 1.0-SNAPSHOT for 2021.1.26
* 主体基本完成，但还没有注册属性(肝不动了)
## 1.0-SNAPSHOT for 2021.1.27
* 注册了一些基础属性，但是貌似没考虑弹射物的情况，下次再说
## 1.1-SNAPSHOT for 2021.2.2
* 修复了ArathothAttribute/Condition 的setDefalutConfig方法无效的问题
* 修复了配置文件生成地方十分鬼畜的问题
* 改良了属性注册的onExecute方法，现在大多数情况下可以不写弹射物判断，极为舒适
* 注册一个新属性:AttackRange
* 应该是第一个可用版本
## 1.11-SNAPSHOT for 2021.2.7
* 元素伤害投递改为真伤
* 修复AdditionalHealth逻辑
* 现在可以在注册属性时指定是否在执行之前修正负值(针对一些属性负值执行导致鬼畜的现象)
## 1.12-SNAPSHOT for 2021.2.9
* 支持新版PAPI
* 支持1.16及其以下的全部版本（感谢坏黑和Berry_so的帮助）
* 为每条属性都加上了描述
* 属性查询命令可以tab补全了
## 1.14-SNAPSHOT for 2021.2.10
* 兼容paper
* 增加了一条可供重写的boolean ZeroExecute()方法，规避了大量无用的debug信息，并且极大优化了性能