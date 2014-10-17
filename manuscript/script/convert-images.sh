#!/bin/bash

set -e
cd "$(dirname "$0")/.."

function convert() {
    /Applications/Inkscape.app/Contents/Resources/bin/inkscape \
        -z -e "$2" --export-dpi=300 -w 1800 "$1"
}

convert images/tdd-with-acceptance-tests.svg images/tdd-with-acceptance-tests.png


