#!/usr/bin/env python3
import os, sys, re

def fix_crlf(path):
    if not os.path.exists(path):
        return
    with open(path, 'rb') as f:
        data = f.read()
    changed = False
    if b'\r\n' in data:
        data = data.replace(b'\r\n', b'\n')
        changed = True
    if b'\r' in data and b'\n' not in data:
        data = data.replace(b'\r', b'')
        changed = True
    if changed:
        with open(path, 'wb') as f:
            f.write(data)
        print(f"Fixed: {path}")

def fix_shebang(path):
    if not os.path.exists(path):
        return
    with open(path, 'rb') as f:
        data = f.read()
    if data.startswith(b'#!/bin/sh'):
        data = b'#!/bin/bash\n' + data[len(b'#!/bin/sh\n'):]
        with open(path, 'wb') as f:
            f.write(data)
        print(f"Fixed shebang to bash: {path}")

base = '/home/comfa/gestionproyectos'
files = [
    f'{base}/backend/gradlew',
    f'{base}/backend/gradlew.bat',
    f'{base}/deploy.sh',
    f'{base}/scripts/destino-proyectos.sh',
    f'{base}/scripts/detect-changed-services.sh',
    f'{base}/scripts/verify-deployment.sh',
]
for f in files:
    fix_crlf(f)
    fix_shebang(f)
print("Done fixing line endings and shebangs")