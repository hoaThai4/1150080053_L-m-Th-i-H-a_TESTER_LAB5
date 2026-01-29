## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Bai 5 (Form đăng ký + PostgreSQL)

### 1) Tạo bảng

- Mở pgAdmin, chọn database `lab5`.
- Chạy script: `src/bai5/schema.sql`.

### 2) Thêm PostgreSQL JDBC Driver

Project hiện chưa có driver PostgreSQL.

- Tải `postgresql-42.x.x.jar` (JDBC driver) và đặt vào thư mục `lib/`.
- Trong VS Code, đảm bảo driver trong `lib/` được add vào Java classpath (JAVA PROJECTS → Referenced Libraries).

### 3) Chạy form

- Chạy class: `bai5.TestRunnerBai5` (hoặc `bai5.CustomerRegistrationForm`).

Mặc định kết nối:

- URL: `jdbc:postgresql://localhost:5432/lab5`
- User: `postgres`
- Pass: `12345678`

Có thể override bằng JVM properties:

- `-Ddb.host=... -Ddb.port=5432 -Ddb.name=lab5 -Ddb.user=postgres -Ddb.pass=12345678`
