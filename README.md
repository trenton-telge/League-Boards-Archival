# League-Boards-Archival
Tool to grab the NA League of Legends discussion boards before they're lost forever

This is different from the other heroic archival tool https://github.com/Nemin32/Charon

This will only archive NA boards, but the intent is to fully mirror the look and feel of the site, and to gather everything.
Instead of recursively parsing links, this indexes /new/ and finds all users and assets to serve those pages.

After backing up, links are rewritten in the pages so that they properly reference each other, and can be serves as a mirror.
