# PigeonChickenDupe

PigeonChickenDupe是一个Minecraft服务器插件，它允许玩家将手中的物品复制到鸡身上并生成多个相同的物品。

![image](https://user-images.githubusercontent.com/98635300/235320824-f37109cc-6d2b-4747-9e2d-6ae7f2581cbe.png)

## 安装

1. 前往 [Releases](https://github.com/XzaiCloud/PigeonChickenDupe/releases) 页面下载最新版本的插件。
2. 将插件文件复制到你的服务器插件目录中。
3. 启动服务器，插件将自动加载。

## 使用

1. 玩家手持任何物品，右键点击已成年的鸡，在鸡的头顶上方显示该物品名称，并在鸡的数据文件中记录该鸡和物品的信息。
2. 随着时间的流逝，插件会周期性地循环所有记录的鸡，并在每只鸡所在的位置掉落相应数量的物品。
3. 如果一只鸡死亡，则其与之相关的数据记录也将从文件中删除。

## 配置

插件包含以下配置选项：

- SpawnInterval - 鸡掉落物品的间隔时间（秒）
- SpawnNumber - 每只鸡掉落物品的数量

默认配置可在插件加载时生成，可以在插件目录下的config.yml文件中进行修改。

## 支持

如有问题或建议，请提交 [issues](https://github.com/XzaiCloud/PigeonChickenDupe/issues)，我们会尽快回复。感谢您的支持！

## 版权和许可证

该插件基于 MIT License 开源，您可以自由使用、分发和修改本插件的代码。无需征得作者同意，但请保留原版权声明和许可证信息。
