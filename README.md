# Zero OAuth library

[![Build Status](https://travis-ci.org/zero-88/zero-oauth.svg?branch=master)](https://travis-ci.org/zero-88/zero-oauth)
[![CircleCI](https://circleci.com/gh/zero-88/zero-oauth.svg?style=shield)](https://circleci.com/gh/zero-88/zero-oauth)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=zero-oauth&metric=coverage)](https://sonarcloud.io/component_measures?id=zero-oauth&metric=coverage)
[![Quality Status](https://sonarcloud.io/api/project_badges/measure?project=zero-oauth&metric=alert_status)](https://sonarcloud.io/dashboard?id=zero-oauth)
[![LOC](https://sonarcloud.io/api/project_badges/measure?project=zero-oauth&metric=alert_status)](https://sonarcloud.io/api/project_badges/measure?project=zero-oauth&metric=ncloc)

## Overview

## Specification

- `OAuth1.0a` specification
  - [ietf](https://tools.ietf.org/html/rfc5849)
  - [oauth](https://oauth.net/1/)
- `OAuth2.0` specification
  - [ietf](https://tools.ietf.org/html/rfc6749)
  - [oauth](https://oauth.net/2/)

## Usecases

- Use directly by CLI -> open browser -> Mock Application server for callback url.
- Use in Java client (robot) -> AUTH_CODE | IMPLICIT | CLIENT_CREDENTIALS -> open browser -> Mock application server
- Use in Android without server -> IMPLICIT -> open browser
- Integrate in existing server -> AUTH_CODE
- Use as 3rd server to consume External API -> Docker
