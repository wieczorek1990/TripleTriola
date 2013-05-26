#!/bin/bash
RESIZE=10%

cd cards
for file in *.svg; do
	echo $file
	filename=`basename "$file"`
	newfilename="${filename%.*}"
	newfile="$newfilename"'.jpg'
	blue="$newfilename"'-blue.jpg'
	red="$newfilename"'-red.jpg'
	convert -resize "$RESIZE" "$file" "$newfile"
	convert -background 'rgb(104, 125, 207)' -resize "$RESIZE" "$file" "$blue"
	convert -background 'rgb(249, 47, 16)' -resize "$RESIZE" "$file" "$red"
done
