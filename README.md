# study_repo1.0

用于学习的仓库-1.0  
author@liyi

## 仓库提交内容

- todo-list markdown格式, 按年月和用户名命令，尽量将各个任务细分。
使用勾选框的格式标记每个任务，月末勾选是否完成
- 学习资料(整理的学习路径，链接等也通过markdown提交)
- 学习感悟，总结 markdown格式
- 代码片段， 有意义，能完整运行的代码片段，提交到对应的文件夹下

## 操作规范

- 发现bug，扩展文件树，功能构想等发issue
- 合并代码发pull request
- 每次在本地仓库添加内容先使用 git pull拉取远程分支的更新
- 每个文件加上author, todo-list等文件加上时间和deadline

## 文件简介

- README.MD, 即本文件，仓库级别的说明文档；
- .gitignore, 可在此文件中匹配添加不需要git跟踪的文件， 比如
开发环境的配置文件, .idea, java 字节码文件, python pyc等编译或运行后的文件
- how-to-learn.md, 根目录下的此文件，主要用于介绍梗概性的，基础性的，零碎的，技能相关的较小知识点，比如git的学习, markdown的学习。

## 目录结构--暂定

    - .gitignore
    - README.MD
    - todo-list
        - %Y-%m@username.md
    - go
        - how-to-learn@liyi.md
        - how-to-learn@lyp.md
        - summary
        - code
    - python
        - how-to-learn@liyi.md
        - how-to-learn@lyp.md
        - summary
        - code
    - java
        - how-to-learn@liyi.md
        - how-to-learn@lyp.md
        - summary
        - code
    - frontend-tech-stack
        - how-to-learn@liyi.md
        - how-to-learn@lyp.md
        - summary
        - code
    注：  
    1.summary可以是一个文件夹， 也可以是单个长文件markdown(推荐)，即所有对此技术或语言的感悟总结都写在同一个markdown里面
    2.code文件里，成员代码不用以文件夹区分(即所有人的代码放到一个文件夹)，只需给代码文件注明author， summary文件夹同理
    3.how-to-learn主要是学习资料，路线等整理
    4.以后每一个技术栈可以安装此目录结构扩展, 比如redis， k8s
    5.此仓库主要用于学习，无完整项目，所以与一般项目结构不同, 且可看情况更改