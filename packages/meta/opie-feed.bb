
# A dependency isn't really needed. Just build meta-opie or even world
# and build the feed afterwards.

#DEPENDS = "meta-opie"

# egrep -i Pattern matched against "$Section $Package-Name"
export FEED_PATTERN = "^opie"
export FEED_NAME = "opie"

# egrep -i Pattern matched against "$Section $Package-Name"
export EXCLUDE_FROM_FEED = "-dev$|translations "
inherit split_ipk_feeds


LICENSE = MIT
