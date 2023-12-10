#!/bin/bash

V=""

#get parameters
V=$LABEL

#get highest tag number, and add 0.1.0 if doesn't exist
if [[ $VERSION == '' ]]
then
  VERSION="0.1.0"
fi
echo "Current Version: $VERSION"


#replace . with space so can split into an array
CURRENT_VERSION_PARTS=(${VERSION//./ })

#get number parts
VNUM1=${CURRENT_VERSION_PARTS[0]}
VNUM2=${CURRENT_VERSION_PARTS[1]}
VNUM3=${CURRENT_VERSION_PARTS[2]}

if [[ $V == 'major' ]]
then
  VNUM1=$((VNUM1+1))
elif [[ $V == 'minor' ]]
then
  VNUM2=$((VNUM2+1))
elif [[ $V == 'patch' ]]
then
  VNUM3=$((VNUM3+1))
else
  echo "No version type (https://semver.org/) or incorrect type specified, try: -v[major, minor, patch]"
  exit 1
fi


#create new tag
NEW_TAG="v$VNUM1.$VNUM2.$VNUM3"
echo "($V) updating $VERSION to $NEW_TAG"

#get current hash and see if it already has a tag
GIT_COMMIT=`git rev-parse HEAD`
NEEDS_TAG=`git describe --contains $GIT_COMMIT 2>/dev/null`

#only tag if no tag already
#to publish, need to be logged in to npm, and with clean working directory: `npm login; git stash`
if [ -z "$NEEDS_TAG" ]; then
  npm version $NEW_TAG
  npm publish --access public
  echo "Tagged with $NEW_TAG"
  git push --tags
  git push
else
  echo "Already a tag on this commit"
fi

exit 0