FROM postgres:latest

ENV POSTGRES_USER=linketinder_user
ENV POSTGRES_PASSWORD=linketinder_password
ENV POSTGRES_DB=linketinder

COPY sql/linketinder.sql /docker-entrypoint-initdb.d/
