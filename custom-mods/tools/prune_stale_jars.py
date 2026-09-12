#!/usr/bin/env python3
"""Prune stale mod jars from destination dirs by fabric.mod.json id.

Reads synced jar paths (one per line) from stdin; destinations come from
argv. A dest jar whose mod id matches a synced jar's mod id but has a
different filename is an old version and is deleted.

Match by mod id, NEVER by filename prefix: sibling mods share prefixes
("dungeons-and-taverns-5.3.2" vs
"dungeons-and-taverns-pillager-outpost-overhaul-v3.3", "chalk" vs
"chalk-colorful-addon") and prefix pruning ate them.
"""

import json
import os
import sys
import zipfile


def mod_id(path):
    try:
        with zipfile.ZipFile(path) as z:
            mj = json.loads(z.read("fabric.mod.json"), strict=False)
            return mj.get("id")
    except Exception:
        return None


def main():
    dests = sys.argv[1:]
    jar_list = [line.strip() for line in sys.stdin if line.strip()]

    synced_by_id = {}
    synced_names = set()
    for j in jar_list:
        synced_names.add(os.path.basename(j))
        mid = mod_id(j)
        if mid:
            synced_by_id[mid] = os.path.basename(j)

    pruned = 0
    for dest in dests:
        try:
            files = [f for f in os.listdir(dest) if f.endswith(".jar")]
        except FileNotFoundError:
            continue
        for f in files:
            if f in synced_names:
                continue
            mid = mod_id(os.path.join(dest, f))
            if mid and mid in synced_by_id:
                os.remove(os.path.join(dest, f))
                pruned += 1
                print(f"pruned stale {f} (mod id {mid}, kept {synced_by_id[mid]})")
    print(f"prune: {pruned} stale jar(s) removed")


if __name__ == "__main__":
    main()
