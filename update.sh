# Updates the Slack API from its source. This scripts requires setting up the remote as
# explained in: https://help.github.com/articles/configuring-a-remote-for-a-fork/
# See also: https://help.github.com/articles/syncing-a-fork/
git fetch upstream
git checkout master
git merge upstream/master
