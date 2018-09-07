# Zero OAuth libraries

[![Build Status](https://travis-ci.org/zero-88/zero-oauth.svg?branch=master)](https://travis-ci.org/zero-88/zero-oauth)
[![CircleCI](https://circleci.com/gh/zero-88/zero-oauth.svg?style=shield)](https://circleci.com/gh/zero-88/zero-oauth)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=zero-oauth&metric=coverage)](https://sonarcloud.io/component_measures?id=zero-oauth&metric=coverage)
[![Quality Status](https://sonarcloud.io/api/project_badges/measure?project=zero-oauth&metric=alert_status)](https://sonarcloud.io/dashboard?id=zero-oauth)
[![LOC](https://sonarcloud.io/api/project_badges/measure?project=zero-oauth&metric=ncloc)](https://sonarcloud.io/api/project_badges/measure?project=zero-oauth&metric=ncloc)

## Overview

Zero OAuth libraries is written in `Java` language, which includes: `OAuth client` and `OAuth server` that supports `OAuth v1`, `OAuth v2` and `OpenID Connect`.

It is designed for micro-service, containerization, modularization and plugable.

## Specification

This libraries are followed these specifications:

- `RFC5849`: The OAuth 1.0 Protocol
  - [IETF#RFC5849](https://tools.ietf.org/html/rfc5849)
  - [OAuth1](https://oauth.net/1/)
- `RFC6749`: The OAuth 2.0 Authorization Framework
  - [IETF#rfc6749](https://tools.ietf.org/html/rfc6749)
  - [OAuth2](https://oauth.net/2/)

## Implementation

### Core Implmentation

- [ ] [OAuth 1.0 Client](./client/README.md#OAuth1)
- [ ] [OAuth 2.0 Client](./client/README.md#OAuth2)
- [ ] [OpenID Connect Client](./client/README.md#OpenID)
- [ ] [OAuth 1.0 Server](./server/README.md#OAuth1)
- [ ] [OAuth 2.0 Server](./server/README.md#OAuth2)
- [ ] [OpenID Connect Server](./server/README.md#OpenID)
- [ ] Java 10+

### Login Pass

Loginpass contains lots of connections to [common services](./loginpass/README.md#Services), every connection has a `Profile` that is represented for user info. It supports `OAuth 1`, `OAuth 2` and `OpenID Connect`.
The `Profile` is standardized with [OpenID Connect UserInfo claims](http://openid.net/specs/openid-connect-core-1_0.html#StandardClaims)

### Framework Integrations

Framework integrations with current specification implementations:

<details>
<summary> <a href="https://developer.android.com/">Android</a></summary>

- [ ] OAuth 1/2 Client

</details>

<details>
<summary> <a href="https://hc.apache.org/">Apache HTTP Client</a></summary>

- [ ] OAuth 1/2 Client

</details>

<details>
<summary> <a href="http://square.github.io/okhttp/">Ok HTTP Client</a></summary>

- [ ] OAuth 1/2 Client

</details>

<details>
<summary> <a href="https://vertx.io/">Vertx</a></summary>

- [ ] OAuth 1/2 Client
- [ ] OAuth 1/2 Server
- [ ] OpenID Client
- [ ] OpenID Server

</details>

<details>
<summary> <a href="https://netty.io/">Netty</a></summary>

- [ ] OAuth 1/2 Client
- [ ] OAuth 1/2 Server
- [ ] OpenID Client
- [ ] OpenID Server

</details>

<details>
<summary> <a href="https://spring.io/">Spring</a></summary>

- [ ] OAuth 1/2 Client
- [ ] OAuth 1/2 Server
- [ ] OpenID Client
- [ ] OpenID Server

</details>
