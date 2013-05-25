#!/bin/bash
RESIZE=10%

cd cards
for file in *.svg; do
	echo $file
	filename=`basename "$file"`
	newfile="${filename%.*}"'.jpg'
	convert "$file" -resize "$RESIZE" "$newfile"
done
