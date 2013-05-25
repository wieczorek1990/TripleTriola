#!/bin/bash
rm -rf black/
cp -r white/ black/
cd black/
find . -name '*.svg' -print | xargs sed -i 's/#ffffff/#000000/g'
