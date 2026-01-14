# 🏫 Zhengzhou University Smart Campus (ZZUVisitor-Service)
# 郑州大学智慧校园导览系统

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Docker](https://img.shields.io/badge/docker-compose-green.svg)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen.svg)
![Vue](https://img.shields.io/badge/Vue.js-3.x-4FC08D.svg)
![PostGIS](https://img.shields.io/badge/PostGIS-3.5-blue.svg)

## 📖 项目简介 (Introduction)

这是一个基于 **WebGIS** 技术的 **郑州大学智慧校园导览系统 (Smart Campus Visitor System)**。它专为校园场景设计，集成了校园高精度地图展示、POI 兴趣点查询、**校内路径规划 (最短路径导航)** 以及失物招领等功能，致力于打造便捷的**智慧游客系统**。

本项目采用 **Docker** 容器化部署，无需本地安装繁杂的 GIS 环境，**一键即可运行**。

### ✨ 核心功能
* 🗺️ **校园地图**：基于 Leaflet/Mapbox 的高精度校园底图展示。
* 🛣️ **路径规划**：支持校内任意两点间的**最短路径计算** (基于 pgRouting Dijkstra 算法)。
* 📍 **POI 查询**：教学楼、宿舍、食堂等兴趣点的快速检索与定位。
* 📢 **失物招领**：互动的失物招领信息发布与查询平台。
* 🚌 **公交线路**：校园内部公交线路的可视化展示。

---

## 🛠️ 技术栈 (Tech Stack)

* **前端**：Vue.js, Element UI, Leaflet / Mapbox GL
* **后端**：Java Spring Boot, MyBatis
* **数据库**：PostgreSQL 16
    * ➕ **PostGIS** (处理地理空间几何数据)
    * ➕ **pgRouting** (核心：处理路径规划算法)
* **运维**：Docker & Docker Compose

---

## 🚀 方式一：Docker 一键启动 (推荐)

### 1. 环境准备
在开始之前，请确保你的电脑上已经安装了：
* [Git](https://git-scm.com/)
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Windows/Mac) 或 Docker Engine (Linux)

### 2. 下载项目
打开终端 (Terminal/PowerShell/CMD)，执行：
```bash
# 请将下面的 URL 替换为你实际的 GitHub 仓库地址
git clone [https://github.com/你的用户名/ZZUVisitor-Service.git](https://github.com/你的用户名/ZZUVisitor-Service.git)
cd ZZUVisitor-Service

3. 一键启动 (最重要的一步！)
确保 Docker Desktop 已经运行，然后在项目根目录下执行：

docker compose up -d

⏳ 请耐心等待：

第一次运行需要下载数据库镜像 (约 500MB+)，速度取决于网速。

启动后，数据库会自动执行 sql/ 目录下的脚本进行初始化（建表、灌数据）。

后端服务需要约 15-30 秒启动时间。

4. 访问系统
打开浏览器，访问： 👉 http://localhost

💻 方式二：本地源码启动 (适合开发者)
如果你想修改代码或者不想使用 Docker，可以在本地手动搭建环境运行。

1. 前置准备 (Prerequisites)
你需要在电脑上安装以下软件：

Java JDK 17+

Node.js 16+ (建议使用 LTS 版本)

PostgreSQL 16 (数据库)

PostGIS 插件 (必须安装)

pgRouting 插件 (必须安装，这是最容易报错的一步！)

2. 数据库配置 (最关键的一步)
安装插件：在安装 PostgreSQL 后，请务必使用 Application Stack Builder 安装 PostGIS 扩展（通常 pgRouting 会包含在 PostGIS 的安装包选项中，或者需要单独下载）。

注意：Windows 用户安装 pgRouting 可能比较麻烦，务必确保 CREATE EXTENSION pgrouting; 能在你的本地数据库执行成功。

创建数据库：新建一个名为 zzu_db 的数据库。

导入数据：

先运行 sql/schema.sql (建立表结构)。

再运行 sql/data.sql (导入数据)。

3. 后端启动 (Spring Boot)
进入后端目录：cd zzvisitor-backend

修改配置：打开 src/main/resources/application.yml，修改数据库连接信息：

spring:
  datasource:
    # 如果是本地启动，这里要填 localhost，而不是 docker 里的 db
    url: jdbc:postgresql://localhost:5432/zzu_db
    username: postgres
    password: 你的数据库密码

启动项目：

命令行：mvn spring-boot:run

IDEA：直接找到 VisitorServiceApplication.java 右键 Run。

4. 前端启动 (Vue)
进入前端目录：cd zzvisitor-frontend

安装依赖：
npm install
# 或者使用淘宝镜像
npm install --registry=[https://registry.npmmirror.com](https://registry.npmmirror.com)

启动开发服务器：
npm run dev
# 或者
npm run serve

访问：http://localhost:8080 (端口视具体配置而定)

🆚 启动方式对比

特性,Docker 启动 (推荐小白),本地源码启动 (推荐开发)
上手难度,⭐ (极低，一键运行),⭐⭐⭐⭐ (较高，要配环境)
环境依赖,只要有 Docker 就行,"需要 JDK, Node, PG, PostGIS, pgRouting..."
数据库,镜像自带全套插件，稳,pgRouting 在 Windows 上极其难装
修改代码,修改后需要重新打包镜像，慢,修改后热更新，快
用途,演示、部署、交付,写代码、调试 Bug

📂 目录结构说明

ZZUVisitor-Service
├── docker-compose.yml   # 核心：容器编排文件，定义了数据库、前后端如何运行
├── sql/                 # 数据库脚本
│   ├── schema.sql       # 🏗️ 表结构定义 (含 PostGIS/pgRouting 插件开启)
│   └── data.sql         # 💾 初始演示数据 (POI、路网数据)
├── zzvisitor-backend/   # 后端 Java 源码
├── zzvisitor-frontend/  # 前端 Vue 源码
└── README.md            # 项目说明文档

💡 混合开发建议 (强烈推荐)
鉴于我们在解决 pgRouting 插件时遇到的巨大困难，强烈建议开发时采用混合模式：

数据库依然用 Docker 跑：保留 docker-compose.yml 里的 db 服务运行。

代码在本地跑：在 IDEA 里运行 Java，在 VS Code 里运行 Vue。

连接方式：把本地后端代码的数据库连接指向 localhost:5433 (这是我们在 Docker 里映射出来的那个端口)。

这样既能享受 Docker 数据库的省心，又能享受本地写代码的流畅！

❓ 常见问题 (Troubleshooting)
Q1: 启动后访问 http://localhost 是一片空白或报错？
原因：后端服务可能还没完全启动，或者数据库正在导入大量数据。

解决：请等待 1 分钟后刷新页面。如果还不行，运行 docker compose logs -f backend 查看后端日志。

Q2: 地图能显示，但“路径规划”用不了（报错或无反应）？
原因：数据库的 pgRouting 插件未正确加载。

解决：

确保你没有修改 docker-compose.yml 里的镜像版本。

尝试重启后端：docker restart zzu_backend。

Q3: 报错 "Port 5432 is already allocated"？
原因：你本地电脑上已经安装并运行了 PostgreSQL，占用了 5432 端口。

解决：

方法A：停止本地的 PostgreSQL 服务。

方法B：修改 docker-compose.yml，将 5432:5432 改为 5433:5432，然后用 docker compose up -d 重启。

Q4: 怎么彻底重置数据库？
如果你把数据搞乱了，想恢复初始状态，请执行以下命令（慎用，会删除所有数据）：

docker compose down -v
docker compose up -d

👨‍💻 开发者指南
如果你想修改代码：

修改前端：在 zzvisitor-frontend 目录下开发，修改后需重新构建镜像或挂载目录。

修改后端：在 zzvisitor-backend 目录下开发 (JDK 17+)。

修改数据库：将新的 SQL 语句更新到 sql/ 目录中。


***

### 🏷️ 关于你需要的 GitHub 标签 (Topics)

README 只是文档，要让 GitHub 的搜索功能识别你的项目标签（WebGIS、智慧校园等），你需要执行以下操作：

1.  打开你的 GitHub 仓库主页。
2.  在右上角 **About** (关于) 这一栏旁边，点击 ⚙️ **齿轮图标**。
3.  在 **Topics** 输入框中，输入以下标签并回车（建议使用英文，搜索权重更高）：
    * `webgis`
    * `smart-campus` (智慧校园)
    * `visitor-system` (游客系统)
    * `java`
    * `vue`
    * `postgis`
    * `pgrouting`
