#!/bin/bash

COMMIT_MSG="updating"

if [ "$1" != "" ]; then
  COMMIT_MSG=$1
fi

git submodule foreach git pull origin master
git add conf/xwing-data2
git commit -m "$COMMIT_MSG"
git push origin master
git push heroku master

