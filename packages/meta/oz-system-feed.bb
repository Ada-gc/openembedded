
# A dependency isn't really needed. Just build meta-? or even world
# and build the feed afterwards.

#DEPENDS = "meta-opie"

# egrep -i Pattern matched against "$Section $Package-Name"
export FEED_PATTERN = "\ locale-|glibc-|-dev$|devel"
export FEED_NAME = "openzaurus-system"

# egrep -i Pattern matched against "$Section $Package-Name"
export EXCLUDE_FROM_FEED = ""

inherit split_ipk_feeds


LICENSE = MIT
