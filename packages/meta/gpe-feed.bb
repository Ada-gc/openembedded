
# A dependency isn't really needed. Just build meta-opie or even world
# and build the feed afterwards.

#DEPENDS = "meta-gpe"

# egrep -i Pattern matched against "$Section $Package-Name"
export FEED_PATTERN = "^gpe|^x11"
export FEED_NAME = "gpe"

# egrep -i Pattern matched against "$Section $Package-Name"
export EXCLUDE_FROM_FEED = "-dev$"

inherit split_ipk_feeds
LICENSE = MIT
