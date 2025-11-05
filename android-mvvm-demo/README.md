# Android MVVM 架构示例项目

这是一个使用 MVVM (Model-View-ViewModel) 架构模式的 Android 示例项目。

## 项目概述

本项目展示了如何在 Android 应用中实现 MVVM 架构，包括：
- Model: 数据模型
- View: UI 层（Activity/Fragment）
- ViewModel: 业务逻辑层
- Repository: 数据仓库层

## 技术栈

- **语言**: Java
- **最小 SDK**: 24 (Android 7.0)
- **目标 SDK**: 34 (Android 14)
- **架构**: MVVM (Model-View-ViewModel)
- **核心库**:
  - AndroidX Lifecycle (ViewModel, LiveData)
  - Material Components
  - RecyclerView
  - ConstraintLayout

## 项目结构

```
app/src/main/java/com/example/mvvmdemo/
├── model/              # 数据模型
│   └── User.java       # 用户实体类
├── repository/         # 数据仓库层
│   └── UserRepository.java  # 用户数据管理
├── view/               # UI 层
│   ├── MainActivity.java    # 主界面
│   └── UserAdapter.java     # RecyclerView 适配器
└── viewmodel/          # 业务逻辑层
    └── UserViewModel.java   # 用户视图模型
```

## MVVM 架构说明

### Model (模型层)
- **位置**: `model/User.java`
- **职责**: 定义数据结构和业务实体
- **特点**: 纯数据类，不包含业务逻辑

### Repository (数据仓库层)
- **位置**: `repository/UserRepository.java`
- **职责**: 管理数据源，提供统一的数据访问接口
- **特点**:
  - 使用单例模式
  - 可以整合多个数据源（本地数据库、远程 API 等）
  - 为 ViewModel 提供干净的数据访问 API

### ViewModel (视图模型层)
- **位置**: `viewmodel/UserViewModel.java`
- **职责**:
  - 处理业务逻辑
  - 管理 UI 相关数据
  - 通过 LiveData 向 View 提供数据
- **特点**:
  - 继承自 `androidx.lifecycle.ViewModel`
  - 使用 LiveData 实现数据观察
  - 生命周期感知，配置更改时保持数据

### View (视图层)
- **位置**: `view/MainActivity.java`, `view/UserAdapter.java`
- **职责**:
  - 显示 UI
  - 处理用户交互
  - 观察 ViewModel 的数据变化
- **特点**:
  - 不包含业务逻辑
  - 通过观察 LiveData 更新 UI
  - 通过 ViewModel 执行操作

## 数据流向

```
View (用户操作)
  ↓
ViewModel (处理逻辑)
  ↓
Repository (获取数据)
  ↓
Model (数据实体)
  ↓
LiveData (通知变化)
  ↓
View (更新UI)
```

## 主要功能

1. **用户列表展示**: 使用 RecyclerView 显示用户列表
2. **数据观察**: 通过 LiveData 实现响应式 UI 更新
3. **刷新功能**: 点击 FAB 按钮刷新用户数据
4. **Toast 消息**: 显示操作结果反馈

## 如何运行

1. 使用 Android Studio 打开项目
2. 等待 Gradle 同步完成
3. 连接 Android 设备或启动模拟器
4. 点击 Run 按钮运行应用

## 构建项目

```bash
# 清理构建
./gradlew clean

# 构建 Debug 版本
./gradlew assembleDebug

# 构建 Release 版本
./gradlew assembleRelease

# 运行测试
./gradlew test
```

## MVVM 架构的优势

1. **关注点分离**: 清晰的层次划分，各层职责明确
2. **可测试性**: ViewModel 独立于 Android 组件，易于单元测试
3. **生命周期感知**: ViewModel 在配置更改时保持数据
4. **响应式编程**: 使用 LiveData 实现数据驱动 UI
5. **可维护性**: 代码结构清晰，易于维护和扩展

## 扩展建议

要进一步完善项目，可以考虑：

1. **网络请求**: 集成 Retrofit 实现真实的 API 调用
2. **本地存储**: 使用 Room 数据库持久化数据
3. **依赖注入**: 使用 Dagger/Hilt 实现依赖注入
4. **协程**: 使用 Kotlin Coroutines 处理异步操作
5. **分页加载**: 使用 Paging 3 库实现列表分页
6. **错误处理**: 完善错误处理和用户反馈机制
7. **单元测试**: 为 ViewModel 和 Repository 编写单元测试

## 相关资源

- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [MVVM Pattern](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)

## 许可证

MIT License

## 作者

Android MVVM Demo Project
