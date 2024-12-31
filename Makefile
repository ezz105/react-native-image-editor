# create git tag
VERSION := 0.7.0
GIT_COMMIT_MESSAGE := "Update features and fix bugs"
.PHONY: all git-push-all tag npm-version npm-publish

npm-version:
	@echo "Updating version..."
	@jq '.version = "$(VERSION)"' package.json > "tmp.json" && mv "tmp.json" package.json
	
tag:
	git tag -a $(VERSION) -m "Release $(VERSION)"
	git push origin $(VERSION)

git-push-all:
	git add .
	git commit -m $(GIT_COMMIT_MESSAGE)
	git push origin master

all: git-push-all tag npm-version npm-publish

npm-publish:
	npm publish